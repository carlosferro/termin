package com.ferro.termin.user.customer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerResource {
	
	private CustomerRepository repository;

	public CustomerResource(CustomerRepository repository) {
		super();
		this.repository = repository;
	}
	
	@GetMapping("/customers")
	public List<Customer> retriveCustomers() {
		var findAll = repository.findAll();
		log.info(findAll.toString());
		return findAll;
	}
	
	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody Customer customer) {
		customer.setId(null);
		return repository.save(customer);
	}
}
