package com.wamk.studantregistration.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wamk.studantregistration.models.Student;

public interface StudentRegistrationRepository extends JpaRepository<Student, UUID>{

}
