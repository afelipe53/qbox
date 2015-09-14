package com.nexws.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.nexws.core.persistence.AbstractModel;

@Entity
@Table(name = "qb_setup")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class QBoxSetup extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String rootVolumeFolder;

	@Column(name = "root_volume_folder", length = 100, nullable = false)
	public String getRootVolumeFolder() {
		return this.rootVolumeFolder;
	}

	public void setRootVolumeFolder(String rootVolumeFolder) {
		this.rootVolumeFolder = rootVolumeFolder;
	}
}