package com.example.webdev.repositories;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.Exam;


public interface ExamRepository extends CrudRepository<Exam, Integer> {
	@Transactional
	public Optional<Exam> findByTitle(String str);
}