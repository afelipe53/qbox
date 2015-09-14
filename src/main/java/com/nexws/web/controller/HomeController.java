package com.nexws.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexws.core.model.QBoxFile;
import com.nexws.core.repository.QBoxFileRepository;

@Controller
@RequestMapping("/app")
public class HomeController {

	@Autowired
	private QBoxFileRepository qBoxFileRepository;

	@RequestMapping(value = "/home/{parentFolderId}", method = RequestMethod.GET)
	public String homeWithParentFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model, @PathVariable String parentFolderId) {

		try {
			QBoxFile parentFolder = this.qBoxFileRepository.retrieveById(new Long(parentFolderId));

			model.addAttribute("fileList", this.qBoxFileRepository.retrieveByParentId(parentFolder.getId()));
			model.addAttribute("parentFolder", parentFolder);
			model.addAttribute("breadcrumbList", this.getBreadcrumbList(parentFolder));

		} catch (Exception e) {
			model.addAttribute("fileList", new ArrayList<QBoxFile>());
			model.addAttribute("parentFolder", new QBoxFile());
			model.addAttribute("breadcrumbList", this.getBreadcrumbList(new QBoxFile()));
		}
		return "app/home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeWithoutParentFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {

		QBoxFile parentFolder = new QBoxFile();

		model.addAttribute("fileList", this.qBoxFileRepository.retrieveByParentId(parentFolder.getId()));
		model.addAttribute("parentFolder", parentFolder);
		model.addAttribute("breadcrumbList", this.getBreadcrumbList(parentFolder));

		return "app/home";
	}

	private List<QBoxFile> getBreadcrumbList(QBoxFile file) {

		List<QBoxFile> breadCumb = new ArrayList<QBoxFile>();

		if (file.getId() == null) {
			QBoxFile parent = new QBoxFile();
			parent.setFileName("QBox");
			breadCumb.add(parent);
		}

		while (file != null && file.getId() != null) {

			breadCumb.add(file);
			if (file.getParent() != null) {
				file = this.qBoxFileRepository.retrieveById(file.getParent().getId());
			} else {
				file = null;
				QBoxFile parent = new QBoxFile();
				parent.setFileName("QBox");
				breadCumb.add(parent);
			}
		}

		Collections.reverse(breadCumb);
		return breadCumb;
	}
}
