package com.example.webdev.services;

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
import com.example.webdev.repositories.CourseRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseService {
	@Autowired
	CourseRepository courseRepository;

	TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	String dateFormat = "MMMM dd,yyyy";


	//get all course list
	@GetMapping("/api/course")
	public Iterable<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@PostMapping("/api/course")
	public Course createCourse(@RequestBody Course course) {
		Date currentDate = new Date();
		DateFormat format = new SimpleDateFormat(dateFormat);
		format.setTimeZone(timeZone);
		String strTodayDate = format.format(currentDate);
		try {
			course.setCreated(new SimpleDateFormat("MMMM dd,yyyy").parse(strTodayDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseRepository.save(course);
	}

	@DeleteMapping("/api/course/{courseId}")
	public void deleteCourse(@PathVariable("courseId") int id) {
		courseRepository.deleteById(id);
	}

	@GetMapping("/api/course/{courseId}")	
	public Course findCourseById(@PathVariable("courseId") int id) {
		Optional<Course> data = courseRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}	
}
