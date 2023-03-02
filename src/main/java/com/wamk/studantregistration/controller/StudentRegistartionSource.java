package com.wamk.studantregistration.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping
	public ResponseEntity<List<Student>> findAllStudents(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id){
		Optional<Student> studentOptional = service.findById(id);
		if(!studentOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not foud!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(studentOptional.get());
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Student> updateStuden(@PathVariable UUID id, @RequestBody @Valid Student student){
		student = service.update(id, student);
		return ResponseEntity.ok().body(student);
	}
}
