package com.li.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller("/home")
public class hello {

    @RequestMapping("/home/index")
    public String toIndex() {
        return "home/index";
    }

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

}
