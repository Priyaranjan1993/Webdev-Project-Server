package com.example.webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.webdev.models.UploadData;

public interface UploadDataRepository extends CrudRepository<UploadData, Integer> {

}
