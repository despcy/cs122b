package com.cs122b.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/movie")
public class MovieController {
	
	@RequestMapping("/")
	public String listCustomers() {
		
		return "index";
	}

}
