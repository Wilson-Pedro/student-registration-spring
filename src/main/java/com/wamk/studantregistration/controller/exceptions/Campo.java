package com.wamk.studantregistration.controller.exceptions;

import java.io.Serializable;

public class Campo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String message;
	
	public Campo() {
	}
	
	public Campo(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
