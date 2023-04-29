package com.ferro.termin.appointmenttype;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ferro.termin.user.UserService;
import com.ferro.termin.user.provider.Provider;

@RestController
@CrossOrigin
public class AppointmentTypeResource {

	private UserService userService;

	public AppointmentTypeResource(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/appointment-type/{email}")
	public List<AppointmentType> retriveAppointmentTypes(@PathVariable String email) {

		var provider = userService.findUserByEmail(email);
		
		return ((Provider) provider).getAppointmentTypes();
	}
}
