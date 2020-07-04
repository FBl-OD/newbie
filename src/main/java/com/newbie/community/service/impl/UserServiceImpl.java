package com.newbie.community.service.impl;

import com.newbie.community.dao.UserDao;
import com.newbie.community.entity.User;
import com.newbie.community.service.UserService;
import com.newbie.community.util.CommunityUtil;
import com.newbie.community.util.MailClient;
import com.newbie.community.util.UserIconCreater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User queryById(int id) {
        return userDao.selectById(id);
    }

    @Override
    public User queryByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public User queryByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    @Override
    public boolean deleteById(int id){
        redisTemplate.opsForValue().decrement(userDao.selectById(id).getName());
        userDao.deleteUserById(id);
        if (redisTemplate.hasKey(userDao.selectById(id).getName()) ||
                userDao.isNullByName(userDao.selectById(id).getName()) == 1) {
            return false;
        }
        return true;
    }

    @Override
    public int add(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateStatus(int id, int status) {
        return userDao.updateStatus(id,status);
    }

    /**
     * @描述 根据输入的邮件名向邮件发送验证码
     * @返回 关于邮箱激活码的信息
     */
    @Override
    public Map<String, Object> sendConfirmCode(String email){
        User user = userDao.selectByEmail(email);
        Map<String, Object> map = new HashMap<>();
        if(user != null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }else{
            String confirmCode;
            if(redisTemplate.hasKey(email)){
                if(redisTemplate.opsForValue().get(email) == null){
                    redisTemplate.opsForValue().decrement(email);
                    confirmCode = CommunityUtil.generateUUID().substring(0, 5);
                    redisTemplate.opsForValue().set(email,confirmCode,60 * 5, TimeUnit.SECONDS);
                }
                confirmCode = (String)redisTemplate.opsForValue().get(email);
            }else {
                confirmCode = CommunityUtil.generateUUID().substring(0, 5);
                redisTemplate.opsForValue().set(email,confirmCode,60 * 5, TimeUnit.SECONDS);
            }
            Context context = new Context();
            context.setVariable("email", email);
            context.setVariable("confirmCode", confirmCode);
            String content = templateEngine.process("/site/activation", context);
            mailClient.sendMail(email,"账号激活码",content);
            map.put("emailMsg","验证码发送成功");
        }
        return map;
    }

    /**
     * @描述 根据User对象和验证码比对后注册用户
     * @返回 关于注册结果信息
     */
    @Override
    public Map<String, Object> register(User user, String confirmCode){
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if(user == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(StringUtils.isBlank(user.getName())){
            map.put("usernameMsg","账号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("usernameMsg","密码不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("usernameMsg","邮箱不能为空！");
            return map;
        }
        if(StringUtils.isBlank(confirmCode) || !confirmCode.equals(redisTemplate.opsForValue().get(user.getEmail()))){
            map.put("confirmCodeMsg","验证码错误");
            return map;
        }

        //验证帐号
        User un = userDao.selectByName(user.getName());
        if(un != null){
            map.put("usernameMsg","该账号已存在");
            return map;
        }

        User ue = userDao.selectByEmail(user.getEmail());
        if(ue != null){
            map.put("userEmailMsg","该邮箱已被注册");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        try {
            user.setHeaderUrl(String.format(UserIconCreater.getIconPath()));
        }catch (IOException e){
            map.put("iconMsg","自动头像生成错误");
        }
        user.setCreateTime(new Date());userDao.insert(user);
        return map;
    }

    /**
     * @描述 用户登录处理
     * @返回 用户登陆结果信息
     */
    @Override
    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg", "密码不能为空");
            return map;
        }

        //验证账号
        int isNull = userDao.isNullByName(username);
        if(isNull == 0){
            map.put("usernameMsg","该账号不存在");
            return map;
        }
        User user = userDao.selectByName(username);
        if(user.getStatus() == 1){
            map.put("usernameMsg", "该账号未激活");
            return map;
        }

        //验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        System.out.println(user.getSalt());
        System.out.println(password);
        if(!user.getPassword().equals(password)){
            map.put("passwordMsg", "密码不正确");
            return map;
        }


        //生成登陆凭证
        if(!redisTemplate.hasKey(user.getId())) {
            String ticket = CommunityUtil.generateUUID();
            redisTemplate.opsForValue().set(user.getName(), ticket, 30, TimeUnit.DAYS);
            map.put("ticket",ticket);
            map.put("username",username);
        }
        return map;
    }

    /**
     * @描述 用户主动登出
     * @返回 无
     */
    @Override
    public void logout(String username){
        boolean b = redisTemplate.delete(username);
    }
}
