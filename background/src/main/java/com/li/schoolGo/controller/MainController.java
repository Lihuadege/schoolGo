package com.li.schoolGo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//普通视图转发的控制器
public class MainController {

    @RequestMapping({"toIndex","/"})
    public String home(){
        return "index";
    }

    @RequestMapping("home")
    public String toMainPage(){
        return "home";
    }

    @RequestMapping("basicInfo")
    public String basicInfo(){
        return "personal/info";
    }

    @RequestMapping("toUpdatePwd")
    public String updatePwd(){
        return "personal/password";
    }


}
