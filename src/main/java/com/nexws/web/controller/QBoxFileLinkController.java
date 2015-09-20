package com.nexws.web.controller;

import java.io.IOException;
import java.util.Arrays;

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
@RequestMapping("/share")
public class QBoxFileLinkController {

	@Autowired
	private QBoxFileLinkRepository qBoxFileLinkRepository;
	@Autowired
	private QBoxFileRepository qBoxFileRepository;

	@RequestMapping(value="/{idLink}", method = RequestMethod.GET)
	public String share(@PathVariable Long idLink, HttpServletRequest request, Model model) {

		try {
			QBoxFileLink link = this.qBoxFileLinkRepository.retrieveById(idLink);
			if (link == null) {
				throw new RepositoryException("Arquivo não encontrado");
			}

			if (link.getFile().isFolder()) {
				model.addAttribute("fileList",
						this.qBoxFileRepository.retrieveByParentId(link.getFile().getId(), false));
				model.addAttribute("breadcrumbList",
						this.qBoxFileRepository.getBreadcrumbListForLink(link.getFile(), link));
			} else {
				model.addAttribute("fileList", Arrays.asList(link.getFile()));
			}

			model.addAttribute("linkId", idLink);
			model.addAttribute("userShare", link.getOwner());

		} catch (RepositoryException e) {
			return "";
		}

		return "share";
	}

	@RequestMapping(value="/file/{id}", method = RequestMethod.GET)
	@ResponseBody public byte[] download(@PathVariable Long id, HttpServletRequest request)
			throws IOException {

		try {
			return this.qBoxFileRepository.download(id, false);
		} catch (Exception e) {
			return new byte[0];
		}
	}

	@RequestMapping(value="/{idLink}/{folderId}", method = RequestMethod.GET)
	public String folder(@PathVariable Long idLink, @PathVariable Long folderId, Model model) {

		try {
			QBoxFileLink link = this.qBoxFileLinkRepository.retrieveById(idLink);

			if (link == null) {
				throw new RepositoryException("Arquivo não encontrado");
			}

			model.addAttribute("fileList",
					this.qBoxFileRepository.retrieveByParentId(folderId, false));

			QBoxFile folder = this.qBoxFileRepository.retrieveById(folderId);

			model.addAttribute("breadcrumbList",
					this.qBoxFileRepository.getBreadcrumbListForLink(folder, link));

			model.addAttribute("linkId", idLink);
			model.addAttribute("userShare", link.getOwner());
		} catch (RepositoryException e) {
			return "";
		}

		return "share";
	}
}
