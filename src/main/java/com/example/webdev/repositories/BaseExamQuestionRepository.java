package com.example.webdev.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.BaseExamQuestion;


public interface BaseExamQuestionRepository extends CrudRepository<BaseExamQuestion, Integer> {
	@Transactional
	public Optional<BaseExamQuestion> findByExamId(int id);
}