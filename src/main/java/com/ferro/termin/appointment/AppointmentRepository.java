package com.ferro.termin.appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferro.termin.user.provider.Provider;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByProviderEquals(Provider provider);

	@Query("select a from Appointment a where a.provider.id = :providerId and  a.startDate >=:dayStart and  a.startDate <=:dayEnd")
	List<Appointment> findByProviderIdWithStartInPeriod(@Param("providerId") int providerId,
			@Param("dayStart") LocalDateTime startPeroid, @Param("dayEnd") LocalDateTime endPeroid);
}
