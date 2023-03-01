package com.wamk.studantregistration.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamk.studantregistration.dtos.StudentDTO;
import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.services.StudentRegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student-registration")
public class StudentRegistartionSource {
	
	@Autowired
	private StudentRegistrationService service;
	
	@PostMapping
	public ResponseEntity<Object> saveStudent(@RequestBody @Valid StudentDTO studentDTO){
		var student = new Student();
		BeanUtils.copyProperties(studentDTO, student);
		student.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(student));
	}
}
