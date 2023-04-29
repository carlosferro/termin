package com.ferro.termin.availability;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ferro.termin.appointmenttype.AppointmentType;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

@Entity
public class DayPlan {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "availability_id")
	@JsonBackReference
	private Availability availability;

	@ElementCollection
	private List<TimePeriod> workingInterval;

	public DayPlan() {
		super();
	}

	public DayPlan(Availability availability, List<TimePeriod> workingInterval) {
		super();
		this.availability = availability;
		this.workingInterval = workingInterval;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public List<TimePeriod> getWorkingInterval() {
		return workingInterval;
	}

	public void setWorkingInterval(List<TimePeriod> workingInterval) {
		this.workingInterval = workingInterval;
	}

	public List<TimePeriod> getAllSlots(AppointmentType appointmentType) {
		var slots = workingInterval.stream().flatMap(timePeriod -> {
			List<TimePeriod> result = new ArrayList<>();
			var start = timePeriod.getStartTime().toSecondOfDay();
			var end = timePeriod.getEndTime().toSecondOfDay() - appointmentType.getDuration() * 60;
			for (long t = start; t <= end; t += appointmentType.getDuration() * 60) {
				var slot = new TimePeriod(LocalTime.ofSecondOfDay(t),
						LocalTime.ofSecondOfDay(t + appointmentType.getDuration() * 60));
				result.add(slot);
			}
			return result.stream();
		}).collect(Collectors.toList());
		return slots;
	}

	@Override
	public String toString() {
		return "DayPlan [id=" + id + ", workingInterval=" + workingInterval + "]";
	}
	
	
}
