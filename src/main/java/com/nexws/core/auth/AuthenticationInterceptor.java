package com.nexws.core.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nexws.core.repository.QBoxSetupRepository;
import com.nexws.core.repository.UserRepository;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private QBoxAuthenticationContext qBoxAuthenticationContext;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QBoxSetupRepository qBoxSetupRepository;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object controller) throws Exception {

		request.setCharacterEncoding("UTF-8");

		if (this.qBoxAuthenticationContext.getUser() == null) {
			if (this.qBoxSetupRepository.retrieve().size() == 0) {

				if (request.getRequestURI().contains("setup")) {
					return true;
				} else {
					response.sendRedirect(request.getContextPath() + "/setup");
					return false;
				}
			}
		}

		if(this.freeUrl(request.getRequestURI())) {
			return true;
		}

		if (this.qBoxAuthenticationContext.isAuthenticated()) {
			return true;
		}

		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}

	private boolean freeUrl(String url) {
		List<String> listFreeUrls = new ArrayList<String>();

		listFreeUrls.add("login");
		listFreeUrls.add("do-login");
		listFreeUrls.add("resources");
		listFreeUrls.add("user-register");
		listFreeUrls.add("do-register");
		listFreeUrls.add("setup");

		for (String item : listFreeUrls) {
			if (url.endsWith(item) || url.contains(item)) {
				return true;
			}
		}

		return false;
	}

}
