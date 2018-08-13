package com.example.webdev.models;
import javax.persistence.Entity;

@Entity
public class EssayExamQuestion extends BaseExamQuestion {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
