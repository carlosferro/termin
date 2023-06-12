package com.ferro.termin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ferro.termin.appointment.Appointment;
import com.ferro.termin.appointment.AppointmentRepository;
import com.ferro.termin.appointment.AppointmentStatus;
import com.ferro.termin.appointmenttype.AppointmentType;
import com.ferro.termin.appointmenttype.AppointmentTypeRepository;
import com.ferro.termin.availability.Availability;
import com.ferro.termin.availability.AvailabilityRepository;
import com.ferro.termin.user.Role;
import com.ferro.termin.user.customer.Customer;
import com.ferro.termin.user.customer.CustomerRepository;
import com.ferro.termin.user.provider.Provider;
import com.ferro.termin.user.provider.ProviderRepository;

@SpringBootApplication
public class TerminApplication implements CommandLineRunner {

	private ProviderRepository providerRepository;

	private AppointmentTypeRepository appointmentTypeRepository;

	private AvailabilityRepository workinPlanRepository;

	private CustomerRepository customerRepository;

	private AppointmentRepository appointmentRepository;

	public TerminApplication(ProviderRepository providerRepository, AppointmentTypeRepository appointmentTypeRepository,
			AvailabilityRepository workinPlanRepository, CustomerRepository customerRepository,
			AppointmentRepository appointmentRepository) {
		super();
		this.providerRepository = providerRepository;
		this.appointmentTypeRepository = appointmentTypeRepository;
		this.workinPlanRepository = workinPlanRepository;
		this.customerRepository = customerRepository;
		this.appointmentRepository = appointmentRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TerminApplication.class, args);
	}

	@Override	
	public void run(String... args) throws Exception {
		String encodedPassword = "$2a$10$h/AJueu7Xt9yh3qYuAXtk.WZJ544Uc2kdOKlHu2qQzCh/A3rq46qm";
		Collection<Role> roles = new ArrayList<Role>();
		roles.add(Role.ADMIN);
		List<Appointment> appointments = new ArrayList<>();
		List<AppointmentType> appointmentTypes = new ArrayList<>();
		Availability workingPlan = new Availability();
		workingPlan.setDefaultDayPlan();
		Provider provider = new Provider(1, encodedPassword, "carlos", "ferro", "user@example.com", roles, "cferro",
				workingPlan, appointments, appointmentTypes);

		workingPlan.setProvider(provider);

		var appointmentType = new AppointmentType(1, "Massagem", "Uma Massagem", 50.0, 60, 0, provider, appointments);
		var appointmentType2 = new AppointmentType(2, "Corte", "Um Corte", 50.0, 30, 0, provider, null);
		provider.addAppointmentType(appointmentType);
		provider.addAppointmentType(appointmentType2);
		Customer customer = new Customer(2, encodedPassword, "customer", "ferro", "customer@example.com", roles, null);
		var appointment = new Appointment(LocalDateTime.now(), LocalDateTime.now(), AppointmentStatus.CONFIRMED,
				provider, customer, appointmentType);
		
		providerRepository.save(provider);
		customerRepository.save(customer);
		
		appointmentRepository.save(appointment);
	}

}
