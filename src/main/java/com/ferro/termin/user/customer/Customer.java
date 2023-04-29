package com.ferro.termin.user.customer;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ferro.termin.appointment.Appointment;
import com.ferro.termin.user.Role;
import com.ferro.termin.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
//@PrimaryKeyJoinColumn(name = "id_customer")
public class Customer extends User {

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private List<Appointment> appointments;

	public Customer() {
		super();
	}

	public Customer(Integer id, String password, String name, String lastName, String email, Collection<Role> roles,
			List<Appointment> appointments) {
		super(id, password, name, lastName, email, roles);
		this.appointments = appointments;
	}

}
