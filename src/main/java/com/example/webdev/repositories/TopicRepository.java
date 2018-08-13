package com.example.webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

}
