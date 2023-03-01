package com.wamk.studantregistration.dtos;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {
	
	@NotBlank
	private String name;
	@NotBlank
	private String registration;
	@NotBlank
	private String period;
	@NotBlank
	private String course;
	
	public StudentDTO() {
	}

	public StudentDTO(String name, @NotBlank String registration, @NotBlank String period, @NotBlank String course) {
		super();
		this.name = name;
		this.registration = registration;
		this.period = period;
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}
