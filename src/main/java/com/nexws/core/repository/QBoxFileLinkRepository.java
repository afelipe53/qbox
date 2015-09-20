package com.nexws.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexws.core.auth.QBoxAuthenticationContext;
import com.nexws.core.model.QBoxFile;
import com.nexws.core.model.QBoxFileLink;
import com.nexws.core.persistence.AbstractRepository;
import com.nexws.core.persistence.RepositoryException;

@Service
public class QBoxFileLinkRepository extends AbstractRepository<QBoxFileLink> {

	@Autowired
	private QBoxAuthenticationContext qBoxAuthenticationContext;

	@Override
	public QBoxFileLink createOrUpdate(QBoxFileLink link) throws RepositoryException {

		if (!link.getFile().getOwner().equals(this.qBoxAuthenticationContext.getUser())) {
			throw new RepositoryException("Usuário não possui permissão para executar ação");
		}

		if (link.getId() == null) {
			link.setGeneratedOn(new Date());
			link.setOwner(this.qBoxAuthenticationContext.getUser());
		}

		return super.createOrUpdate(link);
	}

	public void deleteByFile(QBoxFile file) throws RepositoryException {
		List<QBoxFileLink> links = this.retrieveByProperty("file", file);

		if (links != null) {
			for (QBoxFileLink link : links) {
				super.delete(link);
			}
		}
	}

}
