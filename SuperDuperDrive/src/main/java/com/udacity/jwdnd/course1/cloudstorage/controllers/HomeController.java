package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    public HomeController(UserService userService,FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }



    @GetMapping
    public String getHomeView() {
        return "home";
    }



    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
       return this.userService.getCurrentUsername();
    }

    @ModelAttribute("files")
    public List<File> getAllFiles(Model model){
       return this.fileService.getAllFiles(this.userService.getCurrentUserId());
    }

}
