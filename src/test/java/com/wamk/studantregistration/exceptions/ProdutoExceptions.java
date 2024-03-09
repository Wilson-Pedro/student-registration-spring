package com.wamk.studantregistration.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;
import com.wamk.studantregistration.services.StudentRegistrationService;
import com.wamk.studantregistration.services.exceptions.EntityNotFoundException;
import com.wamk.studantregistration.services.exceptions.RegistrationException;

@SpringBootTest
class ProdutoExceptions {

	@Autowired
	private StudentRegistrationService service;
	
	@Autowired
	StudentRegistrationRepository repository;

	@BeforeEach
	void setup() {
		repository.deleteAll();
	}

	@Test
	void EntityNotFoundExceptionTest() {
		
		assertThrows(EntityNotFoundException.class, () -> service.findById(UUID.randomUUID()));
	}
	
	@Test
	void RegistrationExceptionTest() {
		service.save(new Student(null, "Pedro", "202113522009", "6", "Computing Cience", null));
		
		assertThrows(RegistrationException.class, 
				() -> service.save(service.save(new Student(null, "Carlos", "202113522009", "6", "Computing Cience", null))));
	} 

}
