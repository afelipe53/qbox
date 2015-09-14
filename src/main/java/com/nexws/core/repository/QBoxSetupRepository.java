package com.nexws.core.repository;

import java.io.File;

import org.springframework.stereotype.Service;

import com.nexws.core.model.QBoxSetup;
import com.nexws.core.persistence.AbstractRepository;
import com.nexws.core.persistence.RepositoryException;

@Service
public class QBoxSetupRepository extends AbstractRepository<QBoxSetup>  {

	@Override
	public QBoxSetup createOrUpdate(QBoxSetup entity) throws RepositoryException {

		if (this.retrieve().size() > 0) {
			throw new RepositoryException("Este ambiente já está configurado");
		}

		if (this.isStringNullOrBlank(entity.getRootVolumeFolder())) {
			throw new RepositoryException("Por favor informe um valor para o campo");
		}

		File file = new File(entity.getRootVolumeFolder());

		if (!file.isDirectory()) {
			throw new RepositoryException("O caminho informado não corresponde a uma pasta");
		}

		if (!file.exists()) {
			throw new RepositoryException("A pasta informada não existe");
		}

		entity.setRootVolumeFolder(entity.getRootVolumeFolder().replace('\\', '/'));
		return super.createOrUpdate(entity);
	}

	private boolean isStringNullOrBlank(String param) {
		return param == null || param.trim().equals("");
	}
}
