package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, RedirectAttributes redirectAttributes,Model model){

        try {
            this.fileService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("deleteSuccess",true);
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("deleteError", "Error deleting file with ID " + fileId);
        }
        redirectAttributes.addFlashAttribute("files", this.fileService.getAllFiles());
        setPageAttribute(redirectAttributes, "files");

        return "redirect:/home";
    }

    @PostMapping
    public String upload(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes,Model model) throws IOException {
        String fileUploadError = null;
        if (file != null) {
            if (!Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File fileToUpload = new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), this.userService.getCurrentUserId(), file.getBytes());
                System.out.println(fileToUpload);
                if(!fileService.isAlreadyUploaded(fileToUpload)){
                    if (this.fileService.uploadFile(fileToUpload) > 0) {
                        redirectAttributes.addFlashAttribute("fileUploadSuccess", true);
                    } else {
                        fileUploadError = "There was an error uploading your file. Please try again.";
                        redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
                    }
                }else{
                    fileUploadError = "File already exist. Please choose another one and try again.";
                    redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
                }

            }else{
                fileUploadError = "Please choose a file before upload.";
                redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
            }
        }else{
            fileUploadError = "There was an error uploading your file. Please try again.";
            redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
        }
        redirectAttributes.addFlashAttribute("files", this.fileService.getAllFiles());
        setPageAttribute(redirectAttributes, "files");
        return "redirect:/home";
    }

    @GetMapping("/view/{fileId}")
    public String viewFile(@PathVariable String fileId ,RedirectAttributes redirectAttributes, Model model) throws IOException {
        File file = this.fileService.getFileById(Integer.parseInt(fileId));
        model.addAttribute("fileEntity",file);
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        Charset charset = StandardCharsets.UTF_8;
        model.addAttribute("dataFile",resource.getContentAsString(charset));
        setPageAttribute(redirectAttributes, "files");
        return "displayedFile";
    }
    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
        return this.userService.getCurrentUsername();
    }

    @ModelAttribute("note")
    public Note getNote(Model model){
        return new Note();
    }
    @ModelAttribute("files")
    public List<File> getAllFiles(Model model){
        return this.fileService.getAllFiles();
    }
    private void setPageAttribute(RedirectAttributes model, String page) {
        model.addFlashAttribute("page", page);
    }
}
