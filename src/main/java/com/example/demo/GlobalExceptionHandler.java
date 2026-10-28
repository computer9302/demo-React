package com.example.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException e,
                                     RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        return "redirect:/posts";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handlerIllegalArgument(IllegalArgumentException e,
                                        RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        return "redirect:/posts";
    }
}
