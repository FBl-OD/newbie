package com.newbie.community.controller;

import com.newbie.community.Vo.PageVo;
import com.newbie.community.entity.Blog;
import com.newbie.community.entity.Catgory;
import com.newbie.community.entity.User;
import com.newbie.community.service.*;
import com.newbie.community.util.CommunityConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController implements CommunityConst {

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

    @Value("server.servlet.context-path")
    private String contextPath;

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
            long likes = likeService.numOfLike(BLOG_TYPE, blog.getId());
            map.put("likes",likes);
            long views = viewService.views(blog.getId());
            map.put("views",views);
            list.add(map);
        }
        model.addAttribute("blogs",list);
        List<Catgory> catgories = catgoryService.queryAdminCatgories();
        model.addAttribute("categories",catgories);
        return "index";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, HttpServletRequest request, User user) {
        if (userService.queryByName(user.getName()) == null &&
                request.getParameter("email") != null) {
            Map<String, Object> map1 = userService.sendConfirmCode(request.getParameter("email"));
        }

        if (!request.getParameter("confirmCode").isEmpty()) {
            String confirmCode = request.getParameter("confirmCode");
            Map<String, Object> map = userService.register(user, confirmCode);
            System.out.println(map);
            if (map == null || map.isEmpty()) {
                return "/site/login";
            } else {
                model.addAttribute("usernameMsg", map.get("usernameMsg"));
                model.addAttribute("passwordMsg", map.get("passwordMsg"));
                model.addAttribute("emailMsg", map.get("emailMsg"));
                model.addAttribute("confirmCodeMsg", map.get("confirmCodeMsg"));
                return "/site/register";
            }
        }
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("name")String name, @RequestParam("password")String password,
                        Model model, HttpServletResponse response) {
        Map<String, Object> map = userService.login(name, password);
        if (map.containsKey("ticket")) {
            Cookie cookie1 = new Cookie("ticket", map.get("ticket").toString());
            cookie1.setPath(contextPath);
            cookie1.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie1);

            Cookie cookie2 = new Cookie("username", map.get("username").toString());
            cookie2.setPath(contextPath);
            cookie2.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie2);

            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            System.out.println(map.toString());
            return "/site/login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("username") String username){
        userService.logout(REDIS_PREFIX_TICKET+REDIS_SPLIT+username);
        return "redirect:/index";
    }
}
