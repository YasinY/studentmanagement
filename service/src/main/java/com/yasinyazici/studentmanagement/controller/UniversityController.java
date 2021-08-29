package com.yasinyazici.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityController {

    @GetMapping("/university")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
