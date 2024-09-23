package com.tp.hair_salon_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    String HelloWorld(){
        return "HelloWorld";
    }
}
