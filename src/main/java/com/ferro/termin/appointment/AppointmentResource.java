package com.ferro.termin.appointment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ferro.termin.appointmenttype.AppointmentTypeNotFoundException;
import com.ferro.termin.appointmenttype.AppointmentTypeRepository;
import com.ferro.termin.availability.TimePeriod;
import com.ferro.termin.user.UserService;
import com.ferro.termin.user.customer.CustomerRepository;
import com.ferro.termin.user.provider.Provider;
import com.ferro.termin.user.provider.ProviderNotFoundException;
import com.ferro.termin.user.provider.ProviderRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class AppointmentResource {
	private AppointmentRepository appointmentRepository;
	private ProviderRepository providerRepository;
	private CustomerRepository customerRepository;
	private AppointmentService appointmentService;
	private AppointmentTypeRepository appointmentTypeRepository;
	private UserService userService;

	public AppointmentResource(AppointmentRepository appointmentRepository, ProviderRepository providerRepository,
			CustomerRepository customerRepository, AppointmentService appointmentService,
			AppointmentTypeRepository appointmentTypeRepository, UserService userService) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.providerRepository = providerRepository;
		this.customerRepository = customerRepository;
		this.appointmentService = appointmentService;
		this.appointmentTypeRepository = appointmentTypeRepository;
		this.userService = userService;
	}

	@GetMapping("/appointment/{email}")
	public List<Appointment> retriveAppointments(@PathVariable String email) {
		var provider = userService.findUserByEmail(email);
		var appointments = appointmentRepository.findByProviderEquals((Provider) provider);
		return appointments;
	}

	@PostMapping("/appointment/{email}")
	public void createAppointment(@PathVariable String email, @RequestBody Appointment appointment) {
		appointment.setId(null);

		var provider = userService.findUserByEmail(email);

		var customerEmail = appointment.getCustomer().getEmail();

		var customer = customerRepository.findByEmailEquals(customerEmail);

		if (customer == null)
			throw new ProviderNotFoundException("link:" + customerEmail);

		appointment.setProvider((Provider) provider);

		log.info(appointment.toString());
	}

	@GetMapping("/appointment/available-slots/{email}/{appointmentTypeId}/{date}")
	public List<TimePeriod> retriveAvailableSlotsAtDate(@PathVariable String email, @PathVariable int appointmentTypeId,
			@PathVariable LocalDate date) {

		var provider = (Provider) userService.findUserByEmail(email);

		var appointmentType = appointmentTypeRepository.findById(appointmentTypeId).orElse(null);
		if (appointmentType == null)
			throw new AppointmentTypeNotFoundException("appointmentTypeId: " + appointmentTypeId);
		return appointmentService.getSlotsAtDay(provider, appointmentType, date);
	}

}
