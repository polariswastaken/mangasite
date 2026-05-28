package dev.polar.reader.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://binje.dev", "http://localhost:8080"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public String getMe() {
        return "Hello World";
    }

}
