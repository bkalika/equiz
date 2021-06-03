package com.equiz.db.dtos;

import java.io.Serializable;

public abstract class DTO implements Serializable {
	private static final long serialVersionUID = -642572737158628888L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
