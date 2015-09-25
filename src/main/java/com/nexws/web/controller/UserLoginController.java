package com.nexws.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexws.core.auth.QBoxAuthenticationContext;
import com.nexws.core.model.User;
import com.nexws.core.persistence.RepositoryException;
import com.nexws.core.repository.UserRepository;

@Controller
public class UserLoginController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QBoxAuthenticationContext qBoxAuthenticationContext;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		this.qBoxAuthenticationContext.invalidateSession();
		return this.login();
	}

	@RequestMapping(value = "/do-login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setPassword(UserRepository.encrypt(request.getParameter("password")));

		model.addAttribute("email", request.getParameter("email"));
		model.addAttribute("password", request.getParameter("password"));

		try {
			user = this.userRepository.login(user);

			try {
				response.sendRedirect(request.getContextPath() + "/app/home");
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				model.addAttribute("loginMessage", "Erro ao redirecionar página");
			}

			return null;
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e);
			model.addAttribute("loginMessage", e.getMessage());
			return "redirect:login";
		}
	}

}
