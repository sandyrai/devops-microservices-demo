package com.example.app2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class App2Controller {

    @GetMapping("/hello")
    public String hello(){
        return "this is message from app2";
    }
}
