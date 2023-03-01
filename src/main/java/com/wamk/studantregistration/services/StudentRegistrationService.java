package com.wamk.studantregistration.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentRegistrationService {

	@Autowired
	private StudentRegistrationRepository repository;
	
	@Transactional
	public Student save(Student student) {
		return repository.save(student);
	}

	public List<Student> findAll() {
		return repository.findAll();
	}

	public Optional<Student> findById(UUID id) {
		return repository.findById(id);
	}

}
