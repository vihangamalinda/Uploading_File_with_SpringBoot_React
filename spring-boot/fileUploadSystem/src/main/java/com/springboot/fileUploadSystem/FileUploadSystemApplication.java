package com.springboot.fileUploadSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class FileUploadSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadSystemApplication.class, args);
	}

}
