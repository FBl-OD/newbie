package com.newbie.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(path = "/user/console/{id}",method = RequestMethod.GET)
    public String getUserConsolePage(@PathVariable("id")int id){
        return "site/user-console";
    }
}
