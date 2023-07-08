package com.wamk.studantregistration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.wamk.studantregistration.models.user.User;

public interface UserRepository extends JpaRepository<User, String>{

	UserDetails findByLogin(String login);
}
