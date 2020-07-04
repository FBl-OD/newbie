package com.newbie.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name){
        if(request == null || name == null){
            throw new IllegalArgumentException("参数为空!");
        }

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //判断cookies中是否存在username和username对应的ticket两个Cookie

    public static  boolean hasKey(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    for (Cookie cookie1 : cookies) {
                        if (cookie1.getName().equals(cookie.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
