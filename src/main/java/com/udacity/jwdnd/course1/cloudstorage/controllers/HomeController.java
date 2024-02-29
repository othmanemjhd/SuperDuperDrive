package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    public HomeController(UserService userService,FileService fileService,NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }



    @GetMapping
    public String getHomeView(Model model) {
        Note note = new Note();
        model.addAttribute("note", note);
        Credential credential = new Credential();
        model.addAttribute("credential", credential);
        model.addAttribute("page","files");
        return "home";
    }


    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
       return this.userService.getCurrentUsername();
    }

    @ModelAttribute("files")
    public List<File> getAllFiles(Model model){
       return this.fileService.getAllFiles();
    }
    @ModelAttribute("notes")
    public List<Note> getAllNotes(Model model){
        return this.noteService.getAllNotes();
    }

}
