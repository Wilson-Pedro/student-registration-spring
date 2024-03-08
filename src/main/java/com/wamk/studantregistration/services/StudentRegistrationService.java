package com.wamk.studantregistration.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;
import com.wamk.studantregistration.services.exceptions.EntityNotFoundException;
import com.wamk.studantregistration.services.exceptions.RegistrationException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class StudentRegistrationService {

	@Autowired
	private StudentRegistrationRepository repository;
	
	@Transactional
	public Student save(Student student) {
		if(repository.existsByRegistration(student.getRegistration()))
			throw new RegistrationException();
		
		student.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return repository.save(student);
	}

	public List<Student> findAll() {
		return repository.findAll();
	}

	public Student findById(UUID id) {
		return repository.findById(id).orElseThrow
				(() -> new EntityNotFoundException("Id not found: " + id));
	}

	public Student update(UUID id, @Valid Student student) {
		Student studentUpdated = repository.getReferenceById(id);
		studentUpdated = updateData(studentUpdated, student);
		return repository.save(studentUpdated);
	}

	private Student updateData(Student entity, Student student) {
		entity.setName(student.getName());
		entity.setPeriod(student.getPeriod());
		entity.setRegistration(student.getRegistration());
		return entity;
	}

	@Transactional
	public void deleteById(UUID id) {
		repository.delete(repository.findById(id).orElseThrow
				(() -> new EntityNotFoundException("Id not found: " + id)));
	}
}
