package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHomeView() {
        return "home";
    }

    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getName(); // Retrieves the username of the currently authenticated user
        }

        return null; // No user authenticated
    }
}
