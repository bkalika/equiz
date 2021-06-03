package com.equiz.db.dtos;

public class Role extends DTO {
	private static final long serialVersionUID = 4198285481198237650L;
	private Long id;
	private String name;

	public Role() {
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Role [id = " + id + ", name = " + name + "]";
	}
}
