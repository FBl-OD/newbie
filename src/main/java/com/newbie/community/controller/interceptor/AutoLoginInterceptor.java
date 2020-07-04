package com.newbie.community.controller.interceptor;

import com.newbie.community.entity.User;
import com.newbie.community.service.impl.UserServiceImpl;
import com.newbie.community.util.CookieUtil;
import com.newbie.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证
        if(CookieUtil.hasKey(request,"username")) {
            String username = CookieUtil.getValue(request, "username");
            String ticket = CookieUtil.getValue(request, username);
            if (redisTemplate.hasKey(username)) {
                if (redisTemplate.opsForValue().get(username).equals(ticket)) {
                    User user = userServiceImpl.queryByName(username);
                    hostHolder.setUser(user);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user=hostHolder.getUser();
        if(user != null && modelAndView != null){
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
