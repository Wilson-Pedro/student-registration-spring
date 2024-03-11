package com.wamk.studantregistration.services.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(UUID id) {
		super();
	}
}
