package com.equiz.db.dtos;

public class User extends DTO {
	private static final long serialVersionUID = -1958665644106842435L;
	private Long id;
	private String name;
	private String password;
	private Role role;

	public User() {
	}
	
	public User(Long id, String name, Role role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}

	public User(String name, String password, Role role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public User(Long id, String name, String password, Role role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", roleId=" + role + "]";
	}
}
