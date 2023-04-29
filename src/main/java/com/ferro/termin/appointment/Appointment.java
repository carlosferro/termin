package com.ferro.termin.appointment;

import java.time.LocalDateTime;

import com.ferro.termin.appointmenttype.AppointmentType;
import com.ferro.termin.user.User;
import com.ferro.termin.user.customer.Customer;
import com.ferro.termin.user.provider.Provider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Appointment {

	@Id
	@GeneratedValue
	private Integer id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private AppointmentStatus appointmentStatus;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private User provider;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customer;

	@ManyToOne
	@JoinColumn(name = "appointment_type_id")
	private AppointmentType appointmentType;

	public Appointment() {
		super();
	}

	public Appointment(Integer id, LocalDateTime startDate, LocalDateTime endDate, AppointmentStatus appointmentStatus,
			Provider provider, Customer customer, AppointmentType appointmentType) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.appointmentStatus = appointmentStatus;
		this.provider = provider;
		this.customer = customer;
		this.appointmentType = appointmentType;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", provider=" + provider
				+ ", customer=" + customer + ", appointmentType=" + appointmentType + "]";
	}

}
