package com.cs122b.project.Fabflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String menu(HttpSession session, HttpServletResponse httpServletResponse){

        //if not login, redirect to login
        if(session.getAttribute(session.getId()) == null) {
            httpServletResponse.setHeader("Location", "/login");
            httpServletResponse.setStatus(302);
        }

        return "index";
    }
    @RequestMapping("/_dashboard")
    public String dash(HttpSession session, HttpServletResponse httpServletResponse){

        //if not login, redirect to login

        if(session.getAttribute("admin") == null) {
            httpServletResponse.setHeader("Location", "/login");
            httpServletResponse.setStatus(302);
        }

        return "dashboard";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, HttpServletResponse httpServletResponse){
        //if already login, redirect to index
        if(session.getAttribute(session.getId()) != null) {
            httpServletResponse.setHeader("Location", "/");
            httpServletResponse.setStatus(302);
        }
        if(session.getAttribute("admin") != null) {
            httpServletResponse.setHeader("Location", "/_dashboard");
            httpServletResponse.setStatus(302);
        }
        return "login";
    }
}
