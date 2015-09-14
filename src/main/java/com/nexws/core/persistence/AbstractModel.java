package com.nexws.core.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof AbstractModel)) {
			return false;
		}

		AbstractModel abstractModel = (AbstractModel) o;

		if (abstractModel.id == null || this.id == null) {
			return false;
		}
		if (!this.id.equals(abstractModel.id)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (this.id != null ? this.id.hashCode() : super.hashCode());
	}

}
