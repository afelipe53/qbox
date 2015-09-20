package com.nexws.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nexws.core.persistence.AbstractModel;

@Entity
@Table(name = "qb_file_link")
public class QBoxFileLink extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private User owner;
	private QBoxFile file;
	private java.util.Date generatedOn;
	private java.util.Date expiresOn;

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@ManyToOne
	@JoinColumn(name = "id_file", referencedColumnName = "id", nullable = false)
	public QBoxFile getFile() {
		return this.file;
	}

	public void setFile(QBoxFile file) {
		this.file = file;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "generated_on", nullable = false)
	public java.util.Date getGeneratedOn() {
		return this.generatedOn;
	}

	public void setGeneratedOn(java.util.Date generatedOn) {
		this.generatedOn = generatedOn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expires_on")
	public java.util.Date getExpiresOn() {
		return this.expiresOn;
	}

	public void setExpiresOn(java.util.Date expires) {
		this.expiresOn = expires;
	}

}
