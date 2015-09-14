package com.nexws.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexws.core.model.User;
import com.nexws.core.persistence.RepositoryException;
import com.nexws.core.repository.UserRepository;

@Controller
public class UserRegisterController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(UserRegisterController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/user-register", method = RequestMethod.GET)
	public String userRegisterForm(
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		model.addAttribute("user", session.getAttribute("user-form") != null ?
				((User) session.getAttribute("user-form")) : new User());
		session.setAttribute("user-form", null);
		return "user-register";
	}

	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
	public String userRegisterSubmit(
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		User user = new User();

		user.setEmail(request.getParameter("email"));
		user.setName(request.getParameter("name"));
		user.setPassword(request.getParameter("password"));
		user.setLastName(request.getParameter("lastName"));
		user.setAgreeTermsQbox(request.getParameter("agreeTerms") == null ? false : true);

		try {

			this.userRepository.createOrUpdate(user);
			this.userRepository.login(user);
			try {
				response.sendRedirect(request.getContextPath() + "/app/home");

			} catch (IOException e) {
				LOGGER.error(e.getMessage());

				model.addAttribute("validateMessage", "Erro no processamento do seu cadastro");

				session.setAttribute("user-form", user);

				return "redirect:user-register";
			}

		} catch (RepositoryException e) {

			model.addAttribute("validateMessage", e.getMessage());
			session.setAttribute("user-form", user);
			return "redirect:user-register";
		}

		return null;
	}

}
