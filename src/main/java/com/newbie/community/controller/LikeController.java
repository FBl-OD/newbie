package com.newbie.community.controller;

import com.newbie.community.Vo.ResultVo;
import com.newbie.community.service.LikeService;
import com.newbie.community.util.CommunityConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConst {

    private static final Logger logger=LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/blog/like/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResultVo likeBlog(@PathVariable("id")int id){
        ResultVo resultVo = new ResultVo();

        //校验登录

        try{
            likeService.like(101,BLOG_TYPE,id);
            resultVo.setCode(1);
            resultVo.setMsg("点赞成功");
            Map<String,Object> map=new HashMap<>();
            map.put("likes",likeService.numOfLike(BLOG_TYPE,id));
            map.put("status",1);
            resultVo.setData(map);
        }catch (Exception e){
            resultVo.setCode(0);
            resultVo.setMsg("请稍后重试");
            Map<String,Object> map=new HashMap<>();
            map.put("likes",likeService.numOfLike(BLOG_TYPE,id));
            map.put("status",0);
            resultVo.setData(map);
            logger.error("点赞失败"+e.getMessage());
            throw new RuntimeException("点赞失败",e);
        }finally {
            return resultVo;
        }
    }

    @RequestMapping(path = "/blog/unlike/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResultVo unlikeBlog(@PathVariable("id")int id){
        ResultVo resultVo = new ResultVo();

        //校验登录

        try{
            likeService.unlike(101,BLOG_TYPE,id);
            resultVo.setCode(1);
            resultVo.setMsg("已取消点赞");
            Map<String,Object> map=new HashMap<>();
            map.put("likes",likeService.numOfLike(BLOG_TYPE,id));
            map.put("status",0);
            resultVo.setData(map);
        }catch (Exception e){
            resultVo.setCode(0);
            Map<String,Object> map=new HashMap<>();
            map.put("likes",likeService.numOfLike(BLOG_TYPE,id));
            map.put("status",1);
            resultVo.setData(map);
            logger.error("取消点赞失败"+e.getMessage());
            throw new RuntimeException("取消点赞失败",e);
        }finally {
            return resultVo;
        }
    }

}
