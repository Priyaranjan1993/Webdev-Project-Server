package com.example.webdev.repositories;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.TrueOrFalseExamQuestion;

public interface TrueFalseRepository extends CrudRepository<TrueOrFalseExamQuestion, Integer> { 
	
	@Transactional
	public Optional<TrueOrFalseExamQuestion> findByExamId(int id);
}