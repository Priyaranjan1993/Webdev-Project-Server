package com.example.webdev.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdev.models.Appointment;
import com.example.webdev.models.EssayExamQuestion;
import com.example.webdev.models.User;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	@Query("SELECT u FROM Appointment u WHERE u.doctorUID=:doctorUID")
	Iterable<Appointment> findAppointmentByUid(@Param("doctorUID")String doctorUID);
}
