package com.nexws.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexws.core.model.QBoxFile;
import com.nexws.core.model.QBoxFileLink;
import com.nexws.core.persistence.RepositoryException;
import com.nexws.core.repository.QBoxFileLinkRepository;
import com.nexws.core.repository.QBoxFileRepository;

@Controller
@RequestMapping("/app")
public class HomeController {

	@Autowired
	private QBoxFileRepository qBoxFileRepository;
	@Autowired
	private QBoxFileLinkRepository qBoxFileLinkRepository;

	@RequestMapping(value = "/home/{parentFolderId}", method = RequestMethod.GET)
	public String homeWithParentFolder(Model model, @PathVariable String parentFolderId) {

		try {
			QBoxFile parentFolder = this.qBoxFileRepository.retrieveById(new Long(parentFolderId));

			model.addAttribute("fileList", this.qBoxFileRepository.retrieveByParentId(parentFolder.getId(), true));
			model.addAttribute("parentFolder", parentFolder);
			model.addAttribute("breadcrumbList", this.qBoxFileRepository.getBreadcrumbList(parentFolder));

		} catch (Exception e) {
			model.addAttribute("fileList", new ArrayList<QBoxFile>());
			model.addAttribute("parentFolder", new QBoxFile());
			model.addAttribute("breadcrumbList", this.qBoxFileRepository.getBreadcrumbList(new QBoxFile()));
		}

		return "app/home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeWithoutParentFolder(Model model) {

		QBoxFile parentFolder = new QBoxFile();

		model.addAttribute("fileList", this.qBoxFileRepository.retrieveByParentId(parentFolder.getId(), true));
		model.addAttribute("parentFolder", parentFolder);
		model.addAttribute("breadcrumbList", this.qBoxFileRepository.getBreadcrumbList(parentFolder));

		return "app/home";
	}

	@ResponseBody
	@RequestMapping(value = "/generate-link/{id}", method = RequestMethod.POST)
	public String generateFileLink(HttpServletRequest request, Model model, @PathVariable Long id) {

		QBoxFileLink link = new QBoxFileLink();

		try {

			link.setFile(this.qBoxFileRepository.retrieveById(id));
			link = this.qBoxFileLinkRepository.createOrUpdate(link);

		} catch (RepositoryException e) {
			return "";
		}

		return "http://"+ request.getRemoteAddr() + ":" + request.getLocalPort() + "/share/" + link.getId().toString();
	}



}
