package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, Model model){
        String deleteError = null;
        try {
            this.fileService.deleteFile(fileId);
            model.addAttribute("deleteSuccess",true);
        }catch(Exception e){
            model.addAttribute("deleteError", "Error deleting file with ID " + fileId);
        }
        model.addAttribute("files", this.fileService.getAllFiles(this.userService.getCurrentUserId()));
        return "home";
    }

    @PostMapping
    public String upload(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        String fileUploadError = null;
        if (file != null) {
            if (!Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File fileToUpload = new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), this.userService.getCurrentUserId(), file.getBytes());
                if(!fileService.isAlreadyUploaded(fileToUpload)){
                    if (this.fileService.uploadFile(fileToUpload) > 0) {
                        model.addAttribute("fileUploadSuccess", true);
                    } else {
                        fileUploadError = "There was an error uploading your file. Please try again.";
                        model.addAttribute("fileUploadError", fileUploadError);
                    }
                }else{
                    fileUploadError = "File already exist. Please choose another one and try again.";
                    model.addAttribute("fileUploadError", fileUploadError);
                }

            }else{
                fileUploadError = "Please choose a file before upload.";
                model.addAttribute("fileUploadError", fileUploadError);
            }
        }else{
            fileUploadError = "There was an error uploading your file. Please try again.";
            model.addAttribute("fileUploadError", fileUploadError);
        }
        model.addAttribute("files", this.fileService.getAllFiles(this.userService.getCurrentUserId()));
        return "home";
    }
    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
        return this.userService.getCurrentUsername();
    }

}
