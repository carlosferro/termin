package com.ferro.termin.user.provider;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ferro.termin.appointment.Appointment;
import com.ferro.termin.appointmenttype.AppointmentType;
import com.ferro.termin.availability.Availability;
import com.ferro.termin.user.Role;
import com.ferro.termin.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
//@PrimaryKeyJoinColumn(name = "id_provider")
public class Provider extends User {

	private String link;
	
	@OneToOne(mappedBy = "provider", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JsonManagedReference
	@JsonIgnore
	private Availability availability;

	@OneToMany(mappedBy = "provider")
	@JsonIgnore
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "provider", cascade = {CascadeType.ALL})
	@JsonIgnore
	private List<AppointmentType> appointmentTypes;

	public Provider() {
		super();
	}

	public Provider(Integer id, String password, String name, String lastName, String email, Collection<Role> roles,
			String link, Availability availability, List<Appointment> appointments,
			List<AppointmentType> appointmentTypes) {
		super(id, password, name, lastName, email, roles);
		this.link = link;
		this.availability = availability;
		this.appointments = appointments;
		this.appointmentTypes = appointmentTypes;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<AppointmentType> getAppointmentTypes() {
		return appointmentTypes;
	}

	public void setAppointmentTypes(List<AppointmentType> appointmentTypes) {
		this.appointmentTypes = appointmentTypes;
	}

	@Override
	public String toString() {
		return "Provider [link=" + link + "]";
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public void addAppointmentType(AppointmentType appointmentType) {
		appointmentTypes.add(appointmentType);
	}
}
