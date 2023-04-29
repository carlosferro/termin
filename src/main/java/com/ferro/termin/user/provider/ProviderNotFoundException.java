package com.ferro.termin.user.provider;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProviderNotFoundException extends RuntimeException {

	public ProviderNotFoundException(String message) {
		super(message);
	}
}
