package com.example.webdev.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdev.models.Appointment;
import com.example.webdev.models.User;
import com.example.webdev.repositories.AppointmentRepository;
import com.example.webdev.repositories.UserRepository;

@RestController
public class AdminService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	AppointmentRepository appointmentRepository;


	TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	String dateFormat = "MMMM dd,yyyy";

	@RequestMapping(method = RequestMethod.PUT, value="/api/admin/updateProfile/{userId}")
	public User updateProfile(@RequestBody User newuser,@PathVariable("userId") String userId)
	{
		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newuser.getFirstName());
			user.setLastName(newuser.getLastName());
			user.setPhone(newuser.getPhone());
			user.setEmail(newuser.getEmail());
			user.setUsername(newuser.getUsername());
			user.setDateOfBirth(newuser.getDateOfBirth());
			user.setAddress(newuser.getAddress());
			userRepository.save(user);
			return user;
		}
		return null;
	}


	@PostMapping("/api/admin/appointment")
	public Appointment createAppointment(@RequestBody Appointment newAppointment) {
		Optional<User> data = userRepository.findById(newAppointment.getUserId());
		if(data.isPresent()) {
			User user = data.get();
			Date currentDate = new Date();
			DateFormat format = new SimpleDateFormat(dateFormat);
			format.setTimeZone(timeZone);
			String strTodayDate = format.format(currentDate);
			try {
				newAppointment.setCreated(new SimpleDateFormat("MMMM dd,yyyy").parse(strTodayDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newAppointment.setRequestedUser(user);
			newAppointment.setUserId(newAppointment.getUserId());
			return appointmentRepository.save(newAppointment);
		}
		return null;
	}


}
