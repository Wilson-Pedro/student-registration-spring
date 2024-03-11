package com.wamk.studantregistration.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/students")
public class StudentRegistartionSource {
	
	@Autowired
	private StudentRegistrationService studentService;
	
	@PostMapping
	public ResponseEntity<Object> saveStudent(@Valid @RequestBody StudentDTO studentDTO){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(studentService.save(new Student(studentDTO)));
	}
	
	@GetMapping
	public ResponseEntity<List<Student>> findAllStudents(){
		return ResponseEntity.ok(studentService.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Student> findById(@PathVariable UUID id){
		Student student = studentService.findById(id);
		return ResponseEntity.ok(student);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<StudentDTO> updateStuden(@PathVariable UUID id, 
			@RequestBody @Valid StudentDTO studentDTO){
		studentService.update(id, new Student(studentDTO));
		return ResponseEntity.ok(studentDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable UUID id){
		studentService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
