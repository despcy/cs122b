package com.cs122b.project.Fabflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@RequestMapping("/")
public class IndexController {

//    @RequestMapping("/")
//    public String menu(){
//
//        return "index.html";
//    }
//
    @RequestMapping("/login")
    public String login(){
        System.out.println("loggg");
        return "login";
    }
}
