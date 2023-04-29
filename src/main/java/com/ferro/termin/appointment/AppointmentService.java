package com.ferro.termin.appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ferro.termin.appointmenttype.AppointmentType;
import com.ferro.termin.availability.TimePeriod;
import com.ferro.termin.user.provider.Provider;
import com.ferro.termin.user.provider.ProviderNotFoundException;
import com.ferro.termin.user.provider.ProviderRepository;

@Service
public class AppointmentService {

	private AppointmentRepository appointmentRepository;

	public AppointmentService(AppointmentRepository appointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
	}

	public List<TimePeriod> getSlotsAtDay(Provider provider, AppointmentType appointmentType, LocalDate date) {
		var allSlots = provider.getAvailability().getDayPlan(date.getDayOfWeek())
				.getAllSlots(appointmentType);
		var appointments = appointmentRepository.findByProviderIdWithStartInPeroid(provider.getId(),
				date.atStartOfDay(), date.atStartOfDay().plusDays(1));
		var slots = allSlots.stream().filter(slot -> appointments.stream().noneMatch(slot::overlaps)).collect(Collectors.toList());
		return slots;
	}
}
