package com.example.webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdev.models.Appointment;
import com.example.webdev.models.User;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	@Query("SELECT u FROM Appointment u WHERE u.doctorUID=:doctorUID")
	Iterable<Appointment> findAppointmentByUid(@Param("doctorUID")String doctorUID);
	
	@Query(
	        value = "SELECT distinct requested_user_id FROM Appointment u WHERE u.doctorUID=:doctorUID", 
	        nativeQuery=true
	    )
	Iterable<Integer> findUniqueAppointmentsOfDcotor(@Param("doctorUID")String doctorUID);
}
