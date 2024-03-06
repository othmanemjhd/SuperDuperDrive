package com.udacity.jwdnd.course1.cloudstorage.handlers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        // Add error attributes to the model
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorStack", Arrays.toString(ex.getStackTrace()));
        // Return the error view
        return "error";
    }
}
