package com.example.webdev.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdev.models.WidgetNative;


public interface WidgetNativeRepository extends CrudRepository<WidgetNative, Integer> { 
	
/*	@Query("SELECT w FROM WidgetNative w WHERE u.lessonId=:lessonId")
	Iterable<WidgetNative> findAssignmentByLesson(@Param("lessonId") int lessonId);*/
	
}