package com.wamk.studantregistration.dtos;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {
	
	@NotBlank@NotBlank(message = "name is mandatory")
	private String name;
	
	@NotBlank(message = "registration is mandatory")
	private String registration;
	
	@NotBlank(message = "period is mandatory")
	private String period;
	
	@NotBlank(message = "course is mandatory")
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
