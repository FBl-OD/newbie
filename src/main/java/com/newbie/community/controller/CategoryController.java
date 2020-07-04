package com.newbie.community.controller;

import com.newbie.community.Vo.PageVo;
import com.newbie.community.entity.Blog;
import com.newbie.community.entity.User;
import com.newbie.community.service.BlogService;
import com.newbie.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/category/{id}",method = RequestMethod.GET)
    public String getCategoryPage(@PathVariable("id") int id, Model model, PageVo pageVo){
        pageVo.setRows(blogService.queryCountOfSpecifiedCategory(id));
        pageVo.setPath("/category/"+id);

        List<Blog> blogs = blogService.queryByCatgoryId(id,pageVo.getLimit(), pageVo.getOffset());
        List<Map<String,Object>> list=new ArrayList<>();

        for (Blog blog : blogs) {
            Map<String,Object> map=new HashMap<>();
            User user = userService.queryById(blog.getUserId());
            map.put("blog",blog);
            map.put("user",user);
            list.add(map);
        }
        model.addAttribute("blogs",list);
        return "/site/category";
    }
}
