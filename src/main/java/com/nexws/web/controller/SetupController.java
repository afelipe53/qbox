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

import com.nexws.core.model.QBoxSetup;
import com.nexws.core.persistence.RepositoryException;
import com.nexws.core.repository.QBoxSetupRepository;

@Controller
public class SetupController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(SetupController.class);

	@Autowired
	private QBoxSetupRepository qBoxSetupRepository;

	@RequestMapping(value = "/setup", method = RequestMethod.GET)
	public String setup() {
		if (this.qBoxSetupRepository.retrieve().size() > 0) {
			return "app/home";
		}
		return "setup";
	}

	@RequestMapping(value = "/do-setup", method = RequestMethod.POST)
	public String doSetup(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		if (this.qBoxSetupRepository.retrieve().size() > 0) {
			return "app/home";
		}

		QBoxSetup setup = new QBoxSetup();
		setup.setRootVolumeFolder(request.getParameter("rootVolumeFolder"));
		try {
			this.qBoxSetupRepository.createOrUpdate(setup);
		} catch (RepositoryException e) {
			model.addAttribute("setupMessage", e.getMessage());
			return "redirect:setup";
		}

		try {
			response.sendRedirect(request.getContextPath() + "/user-register");
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return null;
	}
}