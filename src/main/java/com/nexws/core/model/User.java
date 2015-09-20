package com.nexws.core.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nexws.core.model.enums.UserLocaleEnum;
import com.nexws.core.model.enums.UserStatusEnum;
import com.nexws.core.persistence.AbstractModel;
import com.nexws.core.persistence.RepositoryException;
import com.nexws.core.repository.UserRepository;

@Entity
@Table(name = "qb_user")
public class User extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private String name;
	private String lastName;
	private String password;
	private String email;
	private UserStatusEnum userStatus;
	private UserLocaleEnum userLocaleEnum;
	private boolean agreeTermsQbox;
	private QBoxSetup qBoxSetup;

	@Column(name = "name", length = 30, nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "last_name", length = 30)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email", length = 254, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 100, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status", length = 30, nullable = false)
	public UserStatusEnum getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_locale", length = 30, nullable = false)
	public UserLocaleEnum getUserLocaleEnum() {
		return this.userLocaleEnum;
	}

	public void setUserLocaleEnum(UserLocaleEnum userLocaleEnum) {
		this.userLocaleEnum = userLocaleEnum;
	}

	@Column(name = "agree_terms_qbox")
	public boolean isAgreeTermsQbox() {
		return this.agreeTermsQbox;
	}

	public void setAgreeTermsQbox(boolean agreeTermsQbox) {
		this.agreeTermsQbox = agreeTermsQbox;
	}

	@ManyToOne
	@JoinColumn(name = "id_setup", referencedColumnName = "id")
	public QBoxSetup getqBoxSetup() {
		return this.qBoxSetup;
	}

	public void setqBoxSetup(QBoxSetup qBoxSetup) {
		this.qBoxSetup = qBoxSetup;
	}

	@Transient
	public String getUserFolder() throws RepositoryException {

		String rootFolder = this.getqBoxSetup().getRootVolumeFolder();
		String userFolder = rootFolder + "/" + UserRepository.encrypt(this.email);

		try {

			File file = new File(userFolder);

			if (!file.exists() || !file.isDirectory()) {
				file.mkdir();
			}
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}

		return userFolder;
	}

}