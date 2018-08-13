package com.example.webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.Module;

public interface ModuleRepository extends CrudRepository<Module, Integer>{
	
}