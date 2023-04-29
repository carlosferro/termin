package com.ferro.termin.user.provider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	Optional<Provider> findByLinkEquals(String link);
	Optional<Provider> findByEmail(String email);
}
