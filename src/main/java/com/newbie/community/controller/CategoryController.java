package com.newbie.community.controller;

import com.newbie.community.Vo.PageVo;
import com.newbie.community.entity.Blog;
import com.newbie.community.entity.Catgory;
import com.newbie.community.entity.User;
import com.newbie.community.service.*;
import com.newbie.community.util.CommunityConst;
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
public class CategoryController implements CommunityConst {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CatgoryService catgoryService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ViewService viewService;

    @RequestMapping(path = "/category/{id}",method = RequestMethod.GET)
    public String getCategoryPage(@PathVariable("id") int id, Model model, PageVo pageVo){
        pageVo.setRows(blogService.queryCountOfSpecifiedCategory(id));
        pageVo.setPath("/category/"+id);
        List<Blog> blogs = blogService.queryByCatgoryId(id,pageVo.getOffset(), pageVo.getLimit());
        List<Map<String,Object>> list=new ArrayList<>();
        Catgory catgory = catgoryService.queryById(id);
        List<Catgory> catgories = catgoryService.queryAdminCatgories();
        for (Blog blog : blogs) {
            Map<String,Object> map=new HashMap<>();
            User user = userService.queryById(blog.getUserId());
            map.put("catgory",catgory);
            map.put("blog",blog);
            map.put("user",user);
            long likes = likeService.numOfLike(BLOG_TYPE, blog.getId());
            map.put("likes",likes);
            long views = viewService.views(blog.getId());
            map.put("views",views);
            list.add(map);
        }
        model.addAttribute("blogs",list);
        model.addAttribute("category",catgory);
        model.addAttribute("categories",catgories);
        return "/site/category";
    }
}
