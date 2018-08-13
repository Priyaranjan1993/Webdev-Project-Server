package com.example.webdev.repositories;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.EssayExamQuestion;


public interface EssayRepository extends CrudRepository<EssayExamQuestion, Integer> {
	@Transactional
	public Optional<EssayExamQuestion> findByExamId(int id);
}