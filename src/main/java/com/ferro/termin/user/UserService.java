package com.ferro.termin.user;

import com.ferro.termin.user.customer.CustomerNotFoundException;
import com.ferro.termin.user.customer.CustomerRepository;
import com.ferro.termin.user.provider.ProviderNotFoundException;
import com.ferro.termin.user.provider.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ProviderRepository providerRepository;
	private CustomerRepository customerRepository;

	public UserService(ProviderRepository providerRepository, CustomerRepository customerRepository) {
        super();
        this.providerRepository = providerRepository;
        this.customerRepository = customerRepository;
    }

    public User findProviderByEmail(String email) {
        var provider = providerRepository.findByEmail(email).orElse(null);
        if (provider == null)
            throw new ProviderNotFoundException("link: " + email);
        return provider;
    }

    public User findCustomerByEmail(String email) {
        var customer = customerRepository.findByEmail(email).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("link: " + email);
        return customer;
    }
}
