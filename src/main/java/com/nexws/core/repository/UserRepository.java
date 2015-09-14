package com.nexws.core.repository;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexws.core.auth.QBoxAuthenticationContext;
import com.nexws.core.model.User;
import com.nexws.core.model.enums.UserLocaleEnum;
import com.nexws.core.model.enums.UserStatusEnum;
import com.nexws.core.persistence.AbstractRepository;
import com.nexws.core.persistence.RepositoryException;

@Service
public class UserRepository extends AbstractRepository<User> {

	@Autowired
	private QBoxAuthenticationContext qBoxAuthenticationContext;
	@Autowired
	private QBoxSetupRepository qBoxSetupRepository;

	@Override
	public User createOrUpdate(User user) throws RepositoryException {

		if (this.isStringNullOrBlank(user.getEmail())) {
			throw new RepositoryException("E-mail é obrigatório");
		}

		if (this.isStringNullOrBlank(user.getName())) {
			throw new RepositoryException("O nome é obrigatório");
		}

		if (user.getId() == null) {

			if (user.getPassword().length() < 6) {
				throw new RepositoryException("A senha precisa ter 6 ou mais caracteres");
			}

			if (!this.validEmail(user.getEmail())) {
				throw new RepositoryException("E-mail inválido");
			}

			if (this.isStringNullOrBlank(user.getPassword())) {
				throw new RepositoryException("A senha é obrigatória");
			}

			if (!user.isAgreeTermsQbox()) {
				throw new RepositoryException("Por favor confirme que você está de acordo com os termos de serviço.");
			}

			List<User> listUser = this.retrieveByProperty("email", user.getEmail());

			if (listUser.size() > 0) {
				throw new RepositoryException("Usuário já existe");
			}

			user.setPassword(UserRepository.encrypt(user.getPassword()));
			user.setqBoxSetup(this.qBoxSetupRepository.retrieve().get(0));

			user.setUserLocaleEnum(UserLocaleEnum.pt_BR);
			user.setUserStatus(UserStatusEnum.ACTIVE);
		}

		return super.createOrUpdate(user);
	}

	public User login(User user) throws RepositoryException {

		List<User> listUser = this.retrieveByProperty("email", user.getEmail());
		if (listUser.size() == 0) {
			throw new RepositoryException("Usuário não encontrado");
		} else {
			if (!listUser.get(0).getPassword().equals(user.getPassword())) {
				throw new RepositoryException("Senha inválida");
			}

			User userLogged = listUser.get(0);
			this.qBoxAuthenticationContext.setUser(userLogged);
			return userLogged;
		}
	}

	private boolean isStringNullOrBlank(String param) {
		return param == null || param.trim().equals("");
	}

	private boolean validEmail(String email) {

		final String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		final Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public static String encrypt(String password) {

		String generated = null;

		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			generated = hexString.toString();

		} catch (NoSuchAlgorithmException e) { } catch (UnsupportedEncodingException e) { }

		return generated;
	}

}