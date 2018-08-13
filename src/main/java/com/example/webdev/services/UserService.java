package com.example.webdev.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;

import com.example.webdev.models.*;
import com.example.webdev.repositories.AppointmentRepository;
import com.example.webdev.repositories.UserRepository;

@RestController
@SessionAttributes("userId")
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	private JavaMailSender mailSender;

	TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	String dateFormat = "MMMM dd,yyyy";

	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}


	@RequestMapping(method = RequestMethod.POST, value="/api/register")
	public List<String> register(@RequestBody User user, HttpSession session)
	{
		List<User> userList = (List<User>) userRepository.findUserByUsername(user.getUsername());
		List<String> u1 = new ArrayList<String>();
		if(userList.isEmpty())
		{
			userRepository.save(user);
			List<User> u = new ArrayList<User>();
			u = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
			String id = Integer.toString(u.get(0).getId());
			session.setAttribute("userId",id);
			session.setAttribute("user",u.get(0));
			u1.add(id);
			u1.add(Boolean.TRUE.toString());
			return u1;
		}
		else {
			return u1;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value="/api/login")
	public List<String> login(@RequestBody User user,HttpSession session)
	{
		//HttpSession session=request.getSession();
		List<User> u = new ArrayList<User>();
		List<String> u1 = new ArrayList<String>();
		u = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
		if(u.size() == 1)
		{
			String id = Integer.toString(u.get(0).getId());
			u1.add(id);
			u1.add(Boolean.TRUE.toString());
			session.setAttribute("userId",id);
			session.setAttribute("user",u.get(0));
			return u1;
		}
		else {
			return u1;
		}

	}

	@RequestMapping(method = RequestMethod.POST, value="/api/login/forgot")
	public List<String> forgotPassword(@RequestBody User us,HttpServletRequest request) {
		List<User> user = new ArrayList<User>();
		List<String> str = new ArrayList<String>();
		user = (List<User>)userRepository.findUserByEmail(us.getEmail());
		String message = null;
		if(user.isEmpty())
		{
			message = "Email Id is not registered.";
			str.add(message);
		}
		else {
			User u = user.get(0);
			u.setToken(UUID.randomUUID().toString());
			userRepository.save(u);
			String url = request.getScheme()+"://"+request.getServerName();
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("priyaranjan9090@gmail.com");
			passwordResetEmail.setTo(u.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + url + ":8080/api/login/reset/" + u.getToken());
			sendEmail(passwordResetEmail);
			message = "success";
			str.add(message);
		}
		return str;
	}

	@RequestMapping(method = RequestMethod.GET, value="/api/login/reset/{token}")
	@ResponseBody
	public ModelAndView resetPage(@PathVariable("token") String token) {
		ModelAndView modelAndView = new ModelAndView("redirect:/jquery/components/login/resetPassword.template.client.html");
		List<User> user = new ArrayList<User>();
		user = (List<User>)userRepository.findUserByToken(token);
		if(!user.isEmpty())
		{
			modelAndView.addObject("token", token);
		}
		else {
			modelAndView.addObject("ErrorMsg",token);
		}

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value="/api/login/reset")
	@ResponseBody
	public List<String> resetPage(ModelAndView modelAndView,@RequestBody User newUser) {
		List<String> str = new ArrayList<String>();
		modelAndView = new ModelAndView("redirect:/jquery/components/login/login.template.client.html");
		List<User> user = new ArrayList<User>();
		user = (List<User>)userRepository.findUserByToken(newUser.getToken());
		if(!user.isEmpty())
		{
			User resetUser = user.get(0);
			resetUser.setPassword(newUser.getPassword());
			resetUser.setToken(null);
			userRepository.save(resetUser);
			str.add("success");
			return str;

		}
		else {
			str.add("error");
		}
		return str;
	}

	@RequestMapping(method = RequestMethod.POST, value="/api/user/logout",produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String logout(HttpSession session)
	{
		String str;
		session.invalidate();
		str = "logged out";
		return str.toString();
	}

	@GetMapping("/api/profile/{userId}")	
	public User findProfileById(@PathVariable("userId") String userId) {
		//String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	/*	@GetMapping("/api/profile/{patientId}")	
	public User findPatientById(@PathVariable("patientId") int patientId) {
		Optional<User> data = userRepository.findById(patientId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}*/

	@RequestMapping(method = RequestMethod.PUT, value="/api/profile/{userId}")
	public User updateProfile(@RequestBody User newuser,@PathVariable("userId") String userId)
	{
		//String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newuser.getFirstName());
			user.setLastName(newuser.getLastName());
			user.setPhone(newuser.getPhone());
			user.setEmail(newuser.getEmail());
			/*user.setRole(newuser.getRole());*/
			user.setDateOfBirth(newuser.getDateOfBirth());
			user.setAddress(newuser.getAddress());
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value="/api/user/checkAdmin")
	public Boolean checkAdmin(HttpSession session)
	{
		User user = (User) session.getAttribute("user");
		String username = user.getUsername();
		List<User> u = new ArrayList<User>();
		u = (List<User>) userRepository.findUserByUsername(username);
		if(!u.get(0).getRole().equals("Admin"))
		{
			return false;
		}
		else {
			return true;
		}

	}

	@RequestMapping(method = RequestMethod.POST, value="/api/user/search")
	public List<User> searchUsers(@RequestBody User user)
	{
		List<User> u = new ArrayList<User>();
		List<User> u1 = new ArrayList<User>();
		u = (List<User>) userRepository.findUserByField(user.getUsername(), user.getFirstName(), user.getLastName(), user.getRole());
		if(u.size() > 0)
		{
			/*String id = Integer.toString(u.get(0).getId());
			u1.add(id);
			u1.add(Boolean.TRUE.toString());*/
			for(User us : u)
			{
				u1.add(us);
			}
			return u1;
		}
		else {
			return u1;
		}

	}

	@RequestMapping(method = RequestMethod.POST, value="/api/user")
	public Boolean createUser(@RequestBody User user)
	{
		List<User> u = new ArrayList<User>();
		u = (List<User>) userRepository.findUserByUsername(user.getUsername());
		if(u.size() > 0)
		{
			return false;
		}
		else {
			userRepository.save(user);
			return true;
		}

	}

	@GetMapping("/api/user")	
	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id)
	{
		userRepository.deleteById(id);
	}

	@GetMapping("/api/user/{userId}")	
	public User findUserById(@PathVariable("userId") int userId) {
		Optional<User> data = userRepository.findById(userId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT, value="/api/user/{userId}")
	public User updateUser(@RequestBody User newuser,@PathVariable("userId") String id)
	{
		Optional<User> data = userRepository.findById(Integer.parseInt(id));
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newuser.getFirstName());
			user.setLastName(newuser.getLastName());
			user.setPassword(newuser.getPassword());
			user.setUsername(newuser.getUsername());
			user.setRole(newuser.getRole());
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@PostMapping("/api/appointment/{userId}")
	public Appointment createAppointment(@RequestBody Appointment newAppointment,@PathVariable("userId") String userId) {
		//String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
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
			newAppointment.setUserId(Integer.parseInt(userId));
			return appointmentRepository.save(newAppointment);
		}
		return null;
	}

	@GetMapping("/api/appointment/{userId}")
	public List<Appointment> findAllAppointmentsOfUser(@PathVariable("userId") String userId) 
	{
		//String uid = (String) session.getAttribute("userId");
		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
		if(data.isPresent()) {
			User user = data.get();
			return user.getAppointmentData();
		}
		return null;
	}

	@GetMapping("/api/appointment/doctor/{docId}")
	public List<Appointment> findAllAppointmentsOfDoctor(@PathVariable("docId") String docId) 
	{
		List<Appointment> appointmentList = (List<Appointment>) appointmentRepository.findAppointmentByUid(docId);
		if(!appointmentList.isEmpty())
		{
			return appointmentList;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT, value="/api/updateAppointment")
	public Appointment updateAppointment(@RequestBody Appointment appointment)
	{
		Optional<Appointment> data = appointmentRepository.findById(appointment.getId());
		if(data.isPresent()) {
			Appointment newAppointment = data.get();
			newAppointment.setComments(appointment.getComments());
			newAppointment.setConfirmation(appointment.getConfirmation());
			appointmentRepository.save(newAppointment);
			return newAppointment;
		}
		return null;
	}

	@DeleteMapping("/api/appointment/{appointmentId}")
	public void deleteAppointment(@PathVariable("appointmentId")int appointmentId) {
		appointmentRepository.deleteById(appointmentId);
	}

}
