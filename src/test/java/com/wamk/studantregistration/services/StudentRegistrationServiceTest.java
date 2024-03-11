package com.wamk.studantregistration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class StudentRegistrationServiceTest {

	@Autowired
	StudentRegistrationRepository repository;

	@Autowired
	StudentRegistrationService service;

	@BeforeEach
	void setup() {
		repository.deleteAll();
	}

	@Test
	@Transactional
	@DisplayName("Should Save The Student Successfully")
	void saveCase01() {
		assertEquals(0, repository.count());

		Student studentSaved = new Student(null, "Wilson", "202413579009", "6", "Computing Cience", null);
		service.save(studentSaved);
		
		assertEquals("Wilson", studentSaved.getName());
		assertEquals("202413579009", studentSaved.getRegistration());
		assertEquals("6", studentSaved.getPeriod());
		assertEquals("Computing Cience", studentSaved.getCourse());
		assertEquals(1, repository.count());
	}

	@Test
	@DisplayName("Should Fetach A List Of Students Successfully")
	void findAllCase01() {
		service.save(new Student(null, "Pedro", "202113522009", "6", "Computing Cience", null));
		
		List<Student> list = service.findAll();

		assertEquals(list.size(), repository.count());
	}

	@Test
	@DisplayName("Should Find The Student From The Id Successfully")
	void findByIdCase01() {
		service.save(new Student(null, "Pedro", "202213522009", "6", "Computing Cience", null));

		UUID id = repository.findAll().get(0).getId();

		Student studentFinded = service.findById(id);
		
		assertEquals("Pedro", studentFinded.getName());
		assertEquals("202213522009", studentFinded.getRegistration());
		assertEquals("6", studentFinded.getPeriod());
		assertEquals("Computing Cience", studentFinded.getCourse());
	}

	@Test
	@Transactional
	@DisplayName("Should Update The Student Successfully")
	void updateCase01() {
		service.save(new Student(null, "Neto", "202013522009", "6", "Computing Cience", null));
		Student student = new Student(null, "Pedro", "202113522009", "6", "Computing Cience", null);

		UUID id = repository.findAll().get(0).getId();
		
		Student studentUpdated = service.update(id, student);
		
		assertEquals("Pedro", studentUpdated.getName());
		assertEquals(1, repository.count());
	}

	@Test
	@DisplayName("Should Find The Student From The Id Successfully")
	void deleteCase01() {
		service.save(new Student(null, "Neto", "202313522009", "6", "Computing Cience", null));
		assertEquals(1, repository.count());

		UUID id = repository.findAll().get(0).getId();

		service.deleteById(id);

		assertEquals(0, repository.count());
	}
}
