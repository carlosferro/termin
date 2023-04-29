package com.ferro.termin.availability;

import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ferro.termin.user.provider.ProviderNotFoundException;
import com.ferro.termin.user.provider.ProviderRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class AvailabilityResource {
	private AvailabilityRepository availabilityRepository;
	private ProviderRepository providerRepository;

	public AvailabilityResource(AvailabilityRepository workingPlanRepository, ProviderRepository providerRepository) {
		super();
		this.availabilityRepository = workingPlanRepository;
		this.providerRepository = providerRepository;
	}

	@GetMapping("/availability/{email}")
	public Availability retriveAvailability(@PathVariable String email) {
		log.info("test1");
		var provider = providerRepository.findByEmail(email).orElse(null);
		if (provider == null)
			throw new ProviderNotFoundException("email: " + email);
		return provider.getAvailability();
	}

	@PutMapping("/availability/{email}")
	public void updateAvailability(@PathVariable String email, @RequestBody Availability availability) {
		log.info(availability.toString());
		var provider = providerRepository.findByEmail(email).orElse(null);
		if (provider == null)
			throw new ProviderNotFoundException("email: " + email);
		var updatedAvailability = provider.getAvailability();
		for (int i = 0; i < availability.getDayPlans().length; i++) {
			updatedAvailability.getDayPlans()[i] = availability.getDayPlans()[i];
		}
		availabilityRepository.save(updatedAvailability);
	}

	@PutMapping("/availability/{email}/{dayIndex}")
	public void dayPlanDayPlan(@PathVariable String email, @PathVariable int dayIndex, @RequestBody DayPlan dayPlan) {
		log.info(dayPlan.toString());
		var provider = providerRepository.findByEmail(email).orElse(null);
		if (provider == null)
			throw new ProviderNotFoundException("email: " + email);
		try {
			var updatedAvailability = provider.getAvailability();
			updatedAvailability.getDayPlans()[dayIndex].setWorkingInterval(dayPlan.getWorkingInterval());
			availabilityRepository.save(updatedAvailability);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("day index: " + dayIndex);
		}

	}
}
