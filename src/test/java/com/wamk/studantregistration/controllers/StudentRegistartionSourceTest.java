package com.wamk.studantregistration.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wamk.studantregistration.dtos.StudentDTO;
import com.wamk.studantregistration.infra.security.TokenService;
import com.wamk.studantregistration.models.Student;
import com.wamk.studantregistration.models.user.AuthenticationDTO;
import com.wamk.studantregistration.models.user.RegisterDTO;
import com.wamk.studantregistration.models.user.User;
import com.wamk.studantregistration.models.user.UserRole;
import com.wamk.studantregistration.repositories.StudentRegistrationRepository;
import com.wamk.studantregistration.repositories.UserRepository;
import com.wamk.studantregistration.services.StudentRegistrationService;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class StudentRegistartionSourceTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	StudentRegistrationService studentRegistrationService;

	@Autowired
	StudentRegistrationRepository studentRegistrationRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	private static String TOKEN = "";
	
	@BeforeEach
	void setup() {
		studentRegistrationRepository.deleteAll();
	}

	
	@Test
	@Order(1)
	void deveRegistraUsuarioComSucesso() {
		userRepository.deleteAll();
		RegisterDTO registerDTO = new RegisterDTO("Wilson", "12345", UserRole.ADMIN);
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
		
		assertNotNull(encryptedPassword);
		assertNotEquals(encryptedPassword, registerDTO.password());
		
		User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.role());
		
		userRepository.save(newUser);
		
		assertEquals(1, userRepository.count());
		assertEquals(UserRole.ADMIN, registerDTO.role());
		
	}
	
	@Test
	@Order(2)
	void deveRealizarLoginComSucesso() {
		AuthenticationDTO dto = new AuthenticationDTO("Wilson", "12345");
		var usernamePassowrd = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassowrd);
		var token = this.tokenService.generateToken((User) auth.getPrincipal());
		
		assertNotNull(token);
		TOKEN = token;
	}
	
	@Test
	@Transactional
	@DisplayName("Should Save The Student Successfully")
	void saveCase01() throws Exception {
		assertEquals(0, studentRegistrationRepository.count());		

		StudentDTO studentDto = new StudentDTO("Wilson", "202413579009", "6", "Computing Cience");

		String jsonRequest = objectMapper.writeValueAsString(studentDto);

		mockMvc.perform(post("/students")
			.header("Authorization", "Bearer " + TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonRequest))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", equalTo("Wilson")))
			.andExpect(jsonPath("$.registration", equalTo("202413579009")))
			.andExpect(jsonPath("$.period", equalTo("6")))
			.andExpect(jsonPath("$.course", equalTo("Computing Cience")));

		assertEquals(1, studentRegistrationRepository.count());	
	}

	@Test
	@DisplayName("Should Fetach A List Of Students Successfully")
	void findAllCase01() throws Exception {
		studentRegistrationRepository.save(new Student(null, "Wilson", "202413579009", "6", "Computing Cience", LocalDateTime.now()));

		mockMvc.perform(get("/students")
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Should Find The Student From The Id Successfully")
	void findByIdCase01() throws Exception {
		studentRegistrationRepository.save(new Student(null, "Wilson", "202413579009", "6", "Computing Cience", LocalDateTime.now()));

		UUID id = studentRegistrationRepository.findAll().get(0).getId();

		mockMvc.perform(get("/students/{id}", id)
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo("Wilson")))
			.andExpect(jsonPath("$.registration", equalTo("202413579009")))
			.andExpect(jsonPath("$.period", equalTo("6")))
			.andExpect(jsonPath("$.course", equalTo("Computing Cience")));
	}

	@Test
	@Transactional
	@DisplayName("Should Update The Student Successfully")
	void updateCase01() throws Exception {
		studentRegistrationRepository.save(new Student(null, "Wilson", "202413579009", "6", "Computing Cience", LocalDateTime.now()));

		StudentDTO studentDto = new StudentDTO("Wilson", "202413579009", "7", "Computing Cience");

		String jsonRequest = objectMapper.writeValueAsString(studentDto);

		UUID id = studentRegistrationRepository.findAll().get(0).getId();
		
		mockMvc.perform(put("/students/{id}", id)
			.header("Authorization", "Bearer " + TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonRequest))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.period", equalTo("7")));

		assertEquals(1, studentRegistrationRepository.count());
	}

	@Test
	@Transactional
	@DisplayName("Should Find The Student From The Id Successfully")
	void deleteCase01() throws Exception {
		studentRegistrationRepository.save(new Student(null, "Wilson", "202413579009", "6", "Computing Cience", LocalDateTime.now()));
		assertEquals(1, studentRegistrationRepository.count());

		UUID id = studentRegistrationRepository.findAll().get(0).getId();

		mockMvc.perform(delete("/students/{id}", id)
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isNoContent());

		assertEquals(0, studentRegistrationRepository.count());
	}

}
