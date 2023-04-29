package com.ferro.termin.availability;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ferro.termin.user.provider.Provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Availability {

	@Id
	@Column(name = "provider_id")
	private int id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "provider_id")
	@JsonIgnore
	private Provider provider;

	@OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private DayPlan[] dayPlans = new DayPlan[7];

	public Availability() {
		super();
	}

	public void setDayPlan(DayOfWeek dayOfWeek, DayPlan dayPlan) {
		int index = dayOfWeek.getValue() - 1;
		dayPlans[index] = dayPlan;
	}

	public DayPlan getDayPlan(DayOfWeek dayOfWeek) {
		int index = dayOfWeek.getValue() - 1;
		return dayPlans[index];
	}
	
	public void setDefaultDayPlan() {
		var defaultWorkingPeriods = new ArrayList<TimePeriod>(
				Arrays.asList(new TimePeriod(LocalTime.parse("07:00"), LocalTime.parse("12:00")), new TimePeriod(LocalTime.parse("13:00"), LocalTime.parse("17:00"))));
		for (int i = 0; i < 7; i++) {
			dayPlans[i] = new DayPlan(this, defaultWorkingPeriods);
		}
	}
	
	
}
