package com.example.webdev.repositories;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;


import com.example.webdev.models.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Integer>{
	
	@Transactional
	public void deleteByLessonId(int id);
	
}
