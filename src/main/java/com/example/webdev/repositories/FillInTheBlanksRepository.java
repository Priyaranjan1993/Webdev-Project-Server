package com.example.webdev.repositories;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.FillInTheBlanksExamQuestion;


public interface FillInTheBlanksRepository extends CrudRepository<FillInTheBlanksExamQuestion, Integer> {
	@Transactional
	public Optional<FillInTheBlanksExamQuestion> findByExamId(int id);
}