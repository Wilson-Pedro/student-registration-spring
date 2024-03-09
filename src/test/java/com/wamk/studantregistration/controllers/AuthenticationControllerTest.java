package com.wamk.studantregistration.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wamk.studantregistration.models.user.AuthenticationDTO;
import com.wamk.studantregistration.models.user.RegisterDTO;
import com.wamk.studantregistration.models.user.UserRole;
import com.wamk.studantregistration.repositories.UserRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class AuthenticationControllerTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	@Order(1)
	@DisplayName("Shoul Register User Successfully")
	void registerCase01() throws Exception {
		assertEquals(0, userRepository.count());
		
		RegisterDTO registerDTO = new RegisterDTO("Wilson", "12345", UserRole.ADMIN);
		
		String jsonRequest = objectMapper.writeValueAsString(registerDTO);
		
		mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
		
		assertEquals(1, userRepository.count());
	}

	@Test
	@Order(2)
	@DisplayName("Shoul Login User Successfully")
	void loginCase01() throws Exception {
		
		AuthenticationDTO AuthenticationDTO = new AuthenticationDTO("Wilson", "12345");
		
		String jsonRequest = objectMapper.writeValueAsString(AuthenticationDTO);
		
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
	}

}
