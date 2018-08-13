package com.example.webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {

}
