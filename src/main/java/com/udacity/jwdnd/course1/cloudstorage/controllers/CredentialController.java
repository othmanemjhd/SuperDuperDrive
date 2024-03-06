package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    public final CredentialService credentialService;
    private HashService hashService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService,EncryptionService encryptionService,UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @ModelAttribute("mycredentials")
    public List<Credential> getAllCredentials(){
        return this.credentialService.getAllCredentials();

    }
    @PostMapping("/update")
    public String updateCredentials(@ModelAttribute("mycredential") Credential c, RedirectAttributes redirectAttributes, Model model){
        String error_update = null;
        Credential credentialUpdated = this.credentialService.getCredentialsById(c.getCredentialid());

        if(credentialUpdated != null){
            credentialUpdated.setUsername(c.getUsername());
            credentialUpdated.setUrl(c.getUrl());
            credentialUpdated.setPassword(this.encryptionService.encryptValue(c.getPassword(),credentialUpdated.getKey()));
            if(this.credentialService.updateCredential(credentialUpdated)>0){

                redirectAttributes.addFlashAttribute("update-success",true);
            }else{
                redirectAttributes.addFlashAttribute("update-error","Error while updating the credentials");
            }

        }else{
            error_update = "Error retrieving your credentials, Please try again !";
            redirectAttributes.addFlashAttribute("error-update",error_update);
        }

        model.addAttribute("mycredentials", this.credentialService.getAllCredentials());
        setPageAttribute(redirectAttributes,"credentials");

        return "redirect:/home";
    }
    @PostMapping("/add")
    public String addCredentials(@ModelAttribute("mycredential") Credential c, RedirectAttributes redirectAttributes, Model model){
        String credential_error = null;
        //hash the password
        String HashedPassword  = this.encryptionService.encryptValue(c.getPassword(),getkeyValue());
        Credential credentialToAdd = new Credential(null,c.getUrl(),c.getUsername(),getkeyValue(),HashedPassword,this.userService.getCurrentUserId());
        if(this.credentialService.addCredential(credentialToAdd) > 0){
            redirectAttributes.addFlashAttribute("success",true);
        }else{
            credential_error = "There was an error adding your credentials";
            redirectAttributes.addFlashAttribute("error",credential_error);
        }
        redirectAttributes.addFlashAttribute("mycredentials",this.getAllCredentials());
        setPageAttribute(redirectAttributes,"credentials");
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String deleteCredentials(@PathVariable String id,RedirectAttributes redirectAttributes,Model model){
        this.credentialService.deleteCredential(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("mycredentials",this.credentialService.getAllCredentials());
        setPageAttribute(redirectAttributes,"credentials");
        return "redirect:/home";
    }


    private String getkeyValue() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[12];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
    private void setPageAttribute(RedirectAttributes model, String page) {
        model.addFlashAttribute("page", page);
    }
}
