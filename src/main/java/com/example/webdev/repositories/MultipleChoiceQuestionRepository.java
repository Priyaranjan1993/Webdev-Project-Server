package com.example.webdev.repositories;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.MultipleChoiceExamQuestion;

public interface MultipleChoiceQuestionRepository extends CrudRepository<MultipleChoiceExamQuestion, Integer> {
	@Transactional
	public Optional<MultipleChoiceExamQuestion> findByExamId(int id);
}