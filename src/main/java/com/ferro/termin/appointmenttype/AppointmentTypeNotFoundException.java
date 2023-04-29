package com.ferro.termin.appointmenttype;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AppointmentTypeNotFoundException extends RuntimeException {

	public AppointmentTypeNotFoundException(String message) {
		super(message);
	}
}
