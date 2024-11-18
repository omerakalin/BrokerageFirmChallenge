package com.brokeragefirmchallenge.bfchallenge.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError(HttpSession session) {
        String continueUrl = (String) session.getAttribute("continue");
        if (continueUrl != null) {
            return "Error: The original request was to " + continueUrl;
        } else {
            return "Error: No saved request found.";
        }
    }
}
