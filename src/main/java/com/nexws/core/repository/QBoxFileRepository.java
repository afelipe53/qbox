package com.nexws.core.repository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexws.core.auth.QBoxAuthenticationContext;
import com.nexws.core.model.QBoxFile;
import com.nexws.core.persistence.AbstractRepository;
import com.nexws.core.persistence.RepositoryException;

@Service
public class QBoxFileRepository extends AbstractRepository<QBoxFile> {

	@Autowired
	private QBoxAuthenticationContext qBoxAuthenticationContext;

	@Override
	public QBoxFile createOrUpdate(QBoxFile qBoxFile) throws RepositoryException {

		qBoxFile = super.createOrUpdate(qBoxFile);

		if (qBoxFile.getFileRealName() == null && !qBoxFile.isFolder()) {
			qBoxFile.generateFileRealName();
			qBoxFile = this.createOrUpdate(qBoxFile);
		}

		return qBoxFile;
	}

	public List<QBoxFile> retrieveByParentId(Long parentId) {

		QBoxFile parent = this.retrieveById(parentId);

		if (parentId != null && !parent.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			return new ArrayList<QBoxFile>();
		}

		StringBuilder builder = new StringBuilder();

		builder.append("SELECT q ");
		builder.append("  FROM com.nexws.core.model.QBoxFile q ");
		builder.append("   WHERE q.owner.id = :userId");

		if (parentId == null) {
			builder.append("     AND q.parent.id is null");
		} else {
			builder.append("     AND q.parent.id = :parentId");
		}

		builder.append("  ORDER BY q.modifiedOn DESC");

		TypedQuery<QBoxFile> query =
				this.getEntityManager().createQuery(builder.toString(), QBoxFile.class);

		if (parentId != null) {
			query.setParameter("parentId", parentId);
		}

		query.setParameter("userId", this.qBoxAuthenticationContext.getUser().getId());

		List<QBoxFile> listFiles = query.getResultList();

		if (listFiles != null) {
			return listFiles;
		}

		return new ArrayList<QBoxFile>();
	}

	public List<QBoxFile> retrieveByParentAndName(QBoxFile parent, String name) {

		if (parent != null && !parent.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			return new ArrayList<QBoxFile>();
		}

		StringBuilder builder = new StringBuilder();

		builder.append("SELECT q ");
		builder.append("  FROM com.nexws.core.model.QBoxFile q ");
		builder.append("   WHERE q.owner = :user");
		builder.append("     AND q.fileName = :name");

		if (parent == null) {
			builder.append("     AND q.parent.id is null");
		} else {
			builder.append("     AND q.parent = :parent");
		}

		TypedQuery<QBoxFile> query =
				this.getEntityManager().createQuery(builder.toString(), QBoxFile.class);

		if (parent != null) {
			query.setParameter("parent", parent);
		}

		query.setParameter("name", name);
		query.setParameter("user", this.qBoxAuthenticationContext.getUser());

		List<QBoxFile> listFiles = query.getResultList();

		if (listFiles != null) {
			return listFiles;
		}

		return new ArrayList<QBoxFile>();
	}

	public QBoxFile uploadFileInRepository(MultipartFile multipartFile, QBoxFile parent) throws RepositoryException {

		List<QBoxFile> listFilesByNameAndParent = this.retrieveByParentAndName(
				parent, multipartFile.getOriginalFilename());

		QBoxFile qBoxFile = new QBoxFile();
		if (listFilesByNameAndParent.size() > 0) {
			qBoxFile = listFilesByNameAndParent.get(0);
		}

		try {

			if (parent != null && !parent.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
				throw new RepositoryException("Usuário não possui permissão para efetuar essa operação");
			}

			if (!multipartFile.isEmpty()) {

				if (qBoxFile.getId() == null) {

					qBoxFile.setFileExtension(multipartFile.getOriginalFilename().substring(
							multipartFile.getOriginalFilename().lastIndexOf('.') + 1,
							multipartFile.getOriginalFilename().length()));

					qBoxFile.setFileName(multipartFile.getOriginalFilename());
					qBoxFile.setFolder(false);
					qBoxFile.setOwner(this.qBoxAuthenticationContext.getUser());
					qBoxFile.setParent(parent);
				}

				qBoxFile.setModifiedOn(new Date());

				qBoxFile = this.createOrUpdate(qBoxFile);

				String userFolder = this.qBoxAuthenticationContext.getUser().getUserFolder();

				byte[] bytes = multipartFile.getBytes();

				File serverFile = new File(userFolder + "/" + qBoxFile.getFileRealName());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
			}

		} catch (Exception e) {
			throw new RepositoryException("Erro ao realizar upload do arquivo.");
		}

		return qBoxFile;
	}

	@Override
	public void delete(QBoxFile file) throws RepositoryException {

		if (!file.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			throw new RepositoryException("Usuário não possui permissão para deletar o arquivo");
		}

		if (file.isFolder()) {

			List<QBoxFile> childrens = this.retrieveByParentId(file.getId());

			for (QBoxFile item : childrens) {
				this.delete(item);
			}

			super.delete(file);

		} else {

			File realFile = new File(file.getOwner().getUserFolder() + "/" + file.getFileRealName());
			realFile.delete();
			super.delete(file);
		}

	}

	public byte[] download(Long id) throws RepositoryException {

		QBoxFile file = this.retrieveById(id);

		if (!file.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			throw new RepositoryException("Usuário não possui permissão para realizar essa operação");
		}

		File fileDowload = new File(file.getOwner().getUserFolder() + "/" + file.getFileRealName());

		byte[] data = null;

		try {
			data = Files.readAllBytes(fileDowload.toPath());

		} catch (Exception e) {
			return new byte[0];
		}

		return data;
	}

	public QBoxFile createFolder(Long parentId, String folderName) throws RepositoryException {

		if (folderName == null) {
			throw new RepositoryException("Nome da pasta não pode estar vazio");
		}

		QBoxFile parent = this.retrieveById(parentId);

		if (parent != null && !parent.getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			throw new RepositoryException("Usuário não possui permissão para executar essa operação");
		}

		QBoxFile folder = new QBoxFile();

		folder.setFileName(folderName);
		folder.setFolder(true);
		folder.setModifiedOn(new Date());
		folder.setOwner(this.qBoxAuthenticationContext.getUser());
		folder.setParent(parent);

		folder = this.createOrUpdate(folder);

		return folder;

	}
}