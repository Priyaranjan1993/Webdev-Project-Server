package com.example.webdev.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.cloudinary.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.webdev.models.Course;
import com.example.webdev.models.UploadData;
import com.example.webdev.models.User;
import com.example.webdev.repositories.UploadDataRepository;
import com.example.webdev.repositories.UserRepository;
import com.example.webdev.services.FileUploadService;
import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class UploadController {

	@Autowired
	FileUploadService fileuploadService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UploadDataRepository uploadDataRepository;

	TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	String dateFormat = "MMMM dd,yyyy";

	List<String> files = new ArrayList<String>();

	@PostMapping("/post/{title}/{userId}")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,@PathVariable("title") String title,@PathVariable("userId") String userId) {
		String message = "";
		JSONObject newObj;
		try {
			newObj = fileuploadService.store(file,title);

			/*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession();
			String uid = (String) session.getAttribute("userId");*/

			Gson gson= new Gson();
			UploadData obj= gson.fromJson(newObj.toString(),UploadData.class);

			Optional<User> data = userRepository.findById(Integer.parseInt(userId));
			if(data.isPresent()) {
				User loggedUser =  data.get();
				Date currentDate = new Date();
				DateFormat format = new SimpleDateFormat(dateFormat);
				format.setTimeZone(timeZone);
				String strTodayDate = format.format(currentDate);
				try {
					obj.setUploaded(new SimpleDateFormat("MMMM dd,yyyy").parse(strTodayDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj.setUser(loggedUser);
				uploadDataRepository.save(obj);
			}
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping("/getallfiles/{userId}")
	public List<UploadData> getListFiles(Model model, @PathVariable("userId") String userId) {
		/*		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());*/

/*		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		String uid = (String) session.getAttribute("userId");*/

		Optional<User> data = userRepository.findById(Integer.parseInt(userId));
		if(data.isPresent()) {
			User user = data.get();
			return user.getData();
		}
		//return ResponseEntity.ok().body(fileNames);
		return null;
	}

	@DeleteMapping("/deleteFiles/{id}")
	public void deleteUploadedData(@PathVariable("id")int docId) {
		uploadDataRepository.deleteById(docId);
	}

	/*	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = fileuploadService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}*/
}
