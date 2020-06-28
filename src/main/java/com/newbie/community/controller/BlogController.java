package com.newbie.community.controller;

import com.newbie.community.entity.Blog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogController {

    @RequestMapping(path = "/blog", method = RequestMethod.GET)
    public String get() {
        return "site/blog";
    }

    @RequestMapping(path = "/blog/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") int id) {
        return "site/blog-detail";
    }


    @RequestMapping(path = "/blog", method = RequestMethod.POST)
    @ResponseBody
    public String add(Blog blog) {

        return null;
    }

}
