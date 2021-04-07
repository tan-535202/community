package com.example.community2.community2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Indexcontroller {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
