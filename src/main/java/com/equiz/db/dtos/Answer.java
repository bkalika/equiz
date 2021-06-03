package com.equiz.db.dtos;

public class Answer extends DTO {
	private static final long serialVersionUID = 2385515091049666879L;
	private Long id;
	private String name;
	private boolean correct;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getIsCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", name=" + name + ", correct=" + correct + "]";
	}

}
