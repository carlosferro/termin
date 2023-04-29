package com.ferro.termin.user;

import org.springframework.stereotype.Service;

import com.ferro.termin.user.provider.ProviderNotFoundException;
import com.ferro.termin.user.provider.ProviderRepository;

@Service
public class UserService {

	private ProviderRepository providerRepository;

	public UserService(ProviderRepository providerRepository) {
		super();
		this.providerRepository = providerRepository;
	}

	public User findUserByEmail(String email) {
		var provider = providerRepository.findByEmail(email).orElse(null);
		if (provider == null)
			throw new ProviderNotFoundException("link: " + email);
		return provider;
	}
}
