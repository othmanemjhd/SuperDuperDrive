package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;


    public NoteController(NoteService noteService,UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addNewNote(@ModelAttribute("note") Note note, RedirectAttributes redirectAttributes,Model model){
        String noteError = null;
        Note noteToSave = new Note(null,note.getNotetitle(),note.getNotedescription(),this.userService.getCurrentUserId());
            if(this.noteService.addNote(noteToSave)>0){
                redirectAttributes.addFlashAttribute("savedNote", true);
            }else{
                noteError = "There was an error saving your note. Please try again.";
                redirectAttributes.addFlashAttribute("errorNote", noteError);
            }


        redirectAttributes.addFlashAttribute("notes",this.noteService.getAllNotes());
        setPageAttribute(redirectAttributes,"notes");
        return "redirect:/home";
    }


    @PostMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable String noteid,RedirectAttributes redirectAttributes,Model model){
        try {
            this.noteService.deleteNote(Integer.parseInt(noteid));
            redirectAttributes.addFlashAttribute("delete-success",true);
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("delete-error","Error deleting note with ID : "+noteid);
        }
        redirectAttributes.addFlashAttribute("notes",this.noteService.getAllNotes());
        redirectAttributes.addFlashAttribute("note", new Note());
        setPageAttribute(redirectAttributes,"notes");
        return "redirect:/home";
    }
    @PostMapping("/update/")
    public String updateNote(@ModelAttribute("note") Note updatedNote,RedirectAttributes redirectAttributes,Model model){
        //Verify if the note exists
        Note noteToUpdate = this.noteService.getNoteById((updatedNote.getNoteid()));
        if (noteToUpdate != null) {
            noteToUpdate.setNotetitle(updatedNote.getNotetitle());
            noteToUpdate.setNotedescription(updatedNote.getNotedescription());
           if(this.noteService.updateNote(noteToUpdate)>0){

               redirectAttributes.addFlashAttribute("update-success",true);
           }else{
               redirectAttributes.addFlashAttribute("update-error","Error while updating the note");
           }
        }else{
            redirectAttributes.addFlashAttribute("update-error","Error retrieving the note");
        }
      redirectAttributes.addFlashAttribute("notes",this.noteService.getAllNotes());
        setPageAttribute(redirectAttributes,"notes");
        return "redirect:/home";
    }
    @ModelAttribute("notes")
    public List<Note> getAllNotes(Model model){
        return this.noteService.getAllNotes();
    }
    private void setPageAttribute(RedirectAttributes model, String page) {
        model.addFlashAttribute("page", page);
    }

}
