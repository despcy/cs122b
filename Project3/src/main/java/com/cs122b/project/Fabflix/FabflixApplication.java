package com.cs122b.project.Fabflix;

import com.cs122b.project.Fabflix.controller.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class FabflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FabflixApplication.class, args);
	}



}
