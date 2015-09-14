package com.nexws.core.auth;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.nexws.core.model.User;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QBoxAuthenticationContext {

	@Autowired
	private HttpSession session;

	public User getUser() {
		User user = null;
		if (this.session.getAttribute("loggedUser") != null) {
			user = (User) this.session.getAttribute("loggedUser");
		}
		return user;
	}

	public void setUser(User user) {
		this.session.setAttribute("loggedUser", user);
	}

	public boolean isAuthenticated() {
		return this.getUser() != null;
	}

	public void invalidateSession() {
		this.session.invalidate();
	}

}
