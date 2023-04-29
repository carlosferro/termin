package com.ferro.termin.appointmenttype;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ferro.termin.appointment.Appointment;
import com.ferro.termin.user.provider.Provider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class AppointmentType {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	private double price;
	private int duration;
	private int slotFrequency;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	@JsonIgnore
	private Provider provider;

	@OneToMany(mappedBy = "appointmentType")
	@JsonIgnore
	private List<Appointment> appointments;

	public AppointmentType() {
		super();
	}

	public AppointmentType(Integer id, String name, String description, double price, int duration, int slotFrequency,
			Provider provider, List<Appointment> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.slotFrequency = slotFrequency;
		this.provider = provider;
		this.appointments = appointments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public int getSlotFrequency() {
		return slotFrequency;
	}

	public void setSlotFrequency(int slotFrequency) {
		this.slotFrequency = slotFrequency;
	}

	@Override
	public String toString() {
		return "AppointmentType [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", duration=" + duration + ", slotFrequency=" + slotFrequency + ", provider=" + provider + "]";
	}

}
