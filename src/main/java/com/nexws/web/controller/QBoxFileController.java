package com.nexws.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nexws.core.model.QBoxFile;
import com.nexws.core.repository.QBoxFileRepository;

@Controller
@RequestMapping("/app")
public class QBoxFileController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(QBoxFileController.class);

	@Autowired
	private QBoxFileRepository qBoxFileRepository;

	@RequestMapping(value = "upload-file", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, Model model) {
		Long idParentFolder = request.getParameter("parentFolder").length() > 0 ?
				new Long(request.getParameter("parentFolder")) : null;

				try {
					QBoxFile parent = this.qBoxFileRepository.retrieveById(idParentFolder);
					this.qBoxFileRepository.uploadFileInRepository(
							this.getMultipartFileFromHttpRequest(request, "file"), parent);
				} catch (Exception e) {
					model.addAttribute("errorOnUploadFile", e.getMessage());
				}

				return "app/home";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, Model model, @PathVariable String id) {
		try {
			QBoxFile file = this.qBoxFileRepository.retrieveById(new Long(id));
			this.qBoxFileRepository.delete(file);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return "";
		}

		return "app/home";
	}

	@RequestMapping(value="/file/{id}", method = RequestMethod.GET)
	@ResponseBody public byte[] download(@PathVariable String id, HttpServletRequest request)
			throws IOException {

		try {
			return this.qBoxFileRepository.download(new Long(id));
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return new byte[0];
		}
	}

	@RequestMapping(value = "/create-folder/{idParentFolder}/{nameFolderToCreate}", method = RequestMethod.POST)
	public String createFolder(HttpServletRequest request, Model model,
			@PathVariable String idParentFolder, @PathVariable String nameFolderToCreate) {

		try {
			Long parentId = idParentFolder.equals("-1") ? null : new Long(idParentFolder);
			this.qBoxFileRepository.createFolder(parentId, nameFolderToCreate);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return "";
		}

		return "app/home";
	}

	private MultipartFile getMultipartFileFromHttpRequest(HttpServletRequest request, String parameterName) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(parameterName);

		return multipartFile;
	}

}
