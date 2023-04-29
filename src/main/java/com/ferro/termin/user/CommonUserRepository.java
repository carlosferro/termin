package com.ferro.termin.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ferro.termin.user.provider.Provider;

@NoRepositoryBean
public interface CommonUserRepository<T extends User> extends JpaRepository<T, Integer> {

	Optional<Provider> findByEmailEquals(String link);

}
