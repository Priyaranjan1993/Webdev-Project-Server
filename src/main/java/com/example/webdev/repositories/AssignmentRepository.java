package com.example.webdev.repositories;
import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.Assignment;


public interface AssignmentRepository extends CrudRepository<Assignment, Integer> { }