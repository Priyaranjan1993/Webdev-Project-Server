package com.example.webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdev.models.Course;
import com.example.webdev.models.Lesson;
import com.example.webdev.models.Module;
import com.example.webdev.repositories.CourseRepository;
import com.example.webdev.repositories.LessonRepository;
import com.example.webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {

	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	CourseRepository courseRepository;


	/*@PostMapping("/api/course/{cid}/module/{mid}/lesson")*/
	@PostMapping("/api/course/module/lesson/{cid}/{mid}")
	public Lesson createLesson(@RequestBody Lesson newLesson,
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Optional<Module> data2 = moduleRepository.findById(moduleId);
			if(data2.isPresent()) {
				Module module = data2.get();
				newLesson.setModule(module);
				return lessonRepository.save(newLesson);
			}
		}
		return null;
	}
	

	@GetMapping("/api/course/module/lesson/{cid}/{mid}")
	public List<Lesson> findAllLessonsForModule(@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId) 
	{
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
			return module.getLessons();
		}
		return null;
	}
	
	@GetMapping("/api/course/module/lesson")
	public Iterable<Lesson> findAllLessons() {
		return lessonRepository.findAll();
	}


	@DeleteMapping("/api/course/module/lesson/{lid}")
	public void deleteLesson(@PathVariable("lid")int lessonId) {
		lessonRepository.deleteById(lessonId);
	}
}
