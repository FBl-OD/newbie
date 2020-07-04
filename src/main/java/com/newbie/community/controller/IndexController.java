package com.newbie.community.controller;

import com.newbie.community.Vo.PageVo;
import com.newbie.community.entity.Blog;
import com.newbie.community.entity.Catgory;
import com.newbie.community.entity.User;
import com.newbie.community.service.BlogService;
import com.newbie.community.service.CatgoryService;
import com.newbie.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CatgoryService catgoryService;

    @RequestMapping(path={"index","/"},method = RequestMethod.GET)
    public String getIndexPage(Model model, PageVo pageVo){
        pageVo.setPath("/index");
        pageVo.setRows(blogService.queryCount(0));
        List<Blog> blogs = blogService.queryAll(0, pageVo.getOffset(), pageVo.getLimit());
        List<Map<String,Object>> list=new ArrayList<>();
        for (Blog blog : blogs) {
            HashMap<String, Object> map = new HashMap<>();
            //截取内容概要
            if(blog.getHtml().length()>50)
                blog.setHtml(blog.getHtml().substring(0,50));
            map.put("blog",blog);
            User user = userService.queryById(blog.getUserId());
            map.put("user",user);
            Catgory catgory = catgoryService.queryById(blog.getCatgoryId());
            map.put("catgory",catgory);
            list.add(map);
        }
        model.addAttribute("blogs",list);
        List<Catgory> catgories = catgoryService.queryAdminCatgories();
        model.addAttribute("categories",catgories);
        return "index";
    }
}
