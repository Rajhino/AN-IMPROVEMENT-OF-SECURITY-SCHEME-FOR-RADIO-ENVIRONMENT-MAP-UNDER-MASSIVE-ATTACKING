package com.global.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.global.system.service.FileUploadService;

@SpringBootApplication
public class SystemApplication implements CommandLineRunner {

	@Autowired
	FileUploadService storageService;
	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}
	

	@Override
  public void run(String... arg) throws Exception {
//    storageService.deleteAll();
    storageService.init();
  }
}


