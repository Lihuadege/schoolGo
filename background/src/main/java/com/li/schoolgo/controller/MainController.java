package com.li.schoolgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//普通视图转发的控制器
public class MainController {

    @RequestMapping({"toIndex","/"})
    public String home(){
        return "index";
    }

    @RequestMapping("home/index")
    public String toMainPage(){
        return "home/index";
    }

}
