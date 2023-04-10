package com.wamk.studantregistration.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;
import com.wamk.studantregistration.services.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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

	public Student findById(UUID id) {
		return repository.findById(id).orElseThrow
				(() -> new EntityNotFoundException("Id not found: " + id));
	}

	public Student update(UUID id, @Valid Student student) {
		Student entity = repository.getReferenceById(id);
		updateData(entity, student);
		return repository.save(entity);
	}

	private void updateData(Student entity, @Valid Student student) {
		entity.setName(student.getName());
		entity.setPeriod(student.getPeriod());
	}

	public void deleteById(UUID id) {
		repository.deleteById(id);
		
	}

	public boolean existsByRegistration(String registration) {
		return repository.existsByRegistration(registration);
	}

}
