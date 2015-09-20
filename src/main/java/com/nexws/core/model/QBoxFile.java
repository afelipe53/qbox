package com.nexws.core.model;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.nexws.core.persistence.AbstractModel;

@Entity
@Table(name = "qb_file")
public class QBoxFile extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileExtension;
	private String fileRealName;
	private User owner;
	private boolean folder;
	private QBoxFile parent;
	private java.util.Date modifiedOn;

	@Column(name = "file_name", length = 100, nullable = false)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_extension", length = 10)
	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Column(name = "file_realname", length = 100)
	public String getFileRealName() {
		return this.fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Column(name = "folder")
	public boolean isFolder() {
		return this.folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	@ManyToOne
	@JoinColumn(name = "id_parent", referencedColumnName = "id")
	public QBoxFile getParent() {
		return this.parent;
	}

	public void setParent(QBoxFile parent) {
		this.parent = parent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	public java.util.Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(java.util.Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Transient
	public void generateFileRealName() {
		if (this.fileRealName == null) {
			this.fileRealName = this.getId().toString().concat(".").concat(this.fileExtension);
		}
	}

	@Transient
	public String getDateModifiedOn() {

		SimpleDateFormat ft =
				new SimpleDateFormat ("E dd/MM/yyyy hh:mm a");

		return ft.format(this.getModifiedOn());
	}
}