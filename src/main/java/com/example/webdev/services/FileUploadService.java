package com.example.webdev.services;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.example.webdev.services.UserService;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;
import com.example.webdev.models.*;

@Service
public class FileUploadService {
	UserService userService = new UserService();

	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			"cloud_name", "priyaranjan",
			"api_key", "923547548328569",
			"api_secret", "KRpmSCg4eoYe86gKNfEX0sZE_nQ"));

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("C:/Users/priya/Desktop/first-app");

	public JSONObject store(MultipartFile file, String title) {
		try {

			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			//Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			
			JSONObject uploadObj = new JSONObject();
			uploadObj.put("title", title);
			uploadObj.put("url", uploadResult.get("secure_url"));

			//userService.preUploadNewData(uploadObj);
			return uploadObj;
			
			//Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
