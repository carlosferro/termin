package com.ferro.termin.user.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ferro.termin.appointmenttype.AppointmentType;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class ProviderResource {
	private ProviderRepository providerRepository;
	private PasswordEncoder passwordEncoder;

	public ProviderResource(ProviderRepository providerRepository, PasswordEncoder passwordEncoder) {
		super();
		this.providerRepository = providerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/providers")
	public List<Provider> retriveProviders() {
		return providerRepository.findAll();
	}

	@PostMapping("/providers")
	public Provider createProvider(@RequestBody Provider provider) {
		log.info(provider.toString());
		provider.setId(null);
		provider.setPassword(passwordEncoder.encode(provider.getPassword()));
		provider.setLink(provider.getName() + provider.getLastName());
		return providerRepository.save(provider);
	}
}
