package com.example.webdev;

import org.springframework.boot.SpringApplication;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.boot.builder.SpringApplicationBuilder;*/
import com.example.webdev.services.FileUploadService;

@SpringBootApplication
public class WebDevSummerApplication {
	
	@Resource
	FileUploadService fileuploadService;

	public static void main(String[] args) {
		/*SpringApplicationBuilder builder = new SpringApplicationBuilder(WebDevSummerApplication.class);
        builder.headless(false).run(args);*/
		SpringApplication.run(WebDevSummerApplication.class, args);
	}
	
	public void run(String...arg) throws Exception {
		fileuploadService.deleteAll();
		fileuploadService.init();
	}
}
