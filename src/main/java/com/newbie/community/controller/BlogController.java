package com.newbie.community.controller;

import com.newbie.community.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    @RequestMapping(path = "/blog/console/editor", method = RequestMethod.GET)
    public String get() {
        System.out.println("haha");
        return "/site/blog-editor";
    }


    @RequestMapping(path = "/blog/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") int id) {
        return "/site/blog-detail";
    }


    @RequestMapping(path = "/blog", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam("title")String title,@RequestParam("content")String content) {

        return null;
    }

}
