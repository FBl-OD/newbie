package com.newbie.community.controller;

import com.newbie.community.Vo.ResultVo;
import com.newbie.community.entity.Blog;
import com.newbie.community.entity.Catgory;
import com.newbie.community.entity.User;
import com.newbie.community.service.BlogService;
import com.newbie.community.service.CatgoryService;
import com.newbie.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CatgoryService catgoryService;

    //TBD:校验登录
    /**
     * 返回文章编辑试图
     * @param model
     * @return
     */
    @RequestMapping(path = "/blog/editor", method = RequestMethod.GET)
    public String getEditorForAdd(Model model) {
        List<Catgory> catgories = catgoryService.queryAdminCatgories();
        model.addAttribute("catgories",catgories);

        //注入当前user
        User user = userService.queryById(101);
        model.addAttribute("user",user);

        return "/site/blog-editor";
    }

    //校验登录
    //校验文章所属权
    /**
     * 返回特定文章的编辑视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(path = "/blog/editor/{id}", method = RequestMethod.GET)
    public String getEditorForUpdate(@PathVariable("id") int id,Model model) {
        Blog blog = blogService.queryById(id);
        List<Catgory> catgories = catgoryService.queryAdminCatgories();

        //注入当前user
        User user = userService.queryById(101);
        model.addAttribute("user",user);

        model.addAttribute("blog",blog);
        model.addAttribute("catgories",catgories);
        return "/site/blog-editor";
    }

    /**
     * 返回特定文章的详情视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(path = "/blog/detail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable(value = "id") int id, Model model) {
        Blog blog = blogService.queryById(id);
        User user = userService.queryById(blog.getUserId());
        model.addAttribute("blog",blog);
        model.addAttribute("user",user);
        model.addAttribute("owner",true);
        return "/site/blog-detail";
    }

    //校验登录

    /**
     * 上传、修改文章的接口
     * @param id
     * @param title
     * @param catgory
     * @param content
     * @param html
     * @return
     */
    @RequestMapping(path = "/blog", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo addBlog(@RequestParam("id") String id,
                            @RequestParam("title")String title,
                            @RequestParam("catgory")String catgory,
                            @RequestParam("content")String content,
                            @RequestParam("html")String html) {
        ResultVo resultVo = new ResultVo();

        //字符处理：判空、特殊字符转义
        if(StringUtils.isBlank("id")||StringUtils.isBlank(title)||StringUtils.isBlank(catgory)
                ||StringUtils.isBlank(content)||StringUtils.isBlank(html)){
            resultVo.setCode(0);
            resultVo.setMsg("文章发布失败");
            return resultVo;
        }

        try {
            int catgoryId=Integer.valueOf(catgory);
            int blogId=Integer.valueOf(id);
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setCatgoryId(catgoryId);
            blog.setContent(content);
            blog.setHtml(html);
            blog.setStatus(0);
            blog.setCreateTime(new Date());

            //获取当前登录对象
            blog.setUserId(101);

            Map<String,Object> map = new HashMap<>();
            if(blogId!=-1) {//执行更新
                blogService.updateBlogById(blogId, blog);
                map.put("url","/blog/detail/"+id);
            }
            else {//执行插入
                blogService.add(blog);
                map.put("url","/blog/detail/"+blog.getId());
            }

            resultVo.setCode(1);
            resultVo.setMsg("文章发布成功");
            resultVo.setData(map);
        }catch (Exception e){
            resultVo.setCode(0);
            resultVo.setMsg("文章发布失败，请稍后重试");
            logger.error("文章发布失败："+e.getMessage());
            throw new RuntimeException("文章发布失败",e);
        }finally {
            return resultVo;
        }
    }


    //校验登录
    //文章所属权校验
    /**
     * 删除特定博客的接口
     * @param id
     * @return
     */
    @RequestMapping(path = "/blog/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResultVo deleteBlog(@RequestParam("id") String id){
        ResultVo resultVo = new ResultVo();

        //权限校验

        try{
            int blogId=Integer.valueOf(id);

            blogService.updateStatus(blogId,1);
            Map<String,Object> map = new HashMap<>();
            resultVo.setCode(1);
            resultVo.setMsg("文章删除成功");
            resultVo.setData(map);
        }catch (Exception e){
            resultVo.setCode(0);
            resultVo.setMsg("文章删除失败，请稍后重试");
            logger.error("文章删除失败："+e.getMessage());
            throw new RuntimeException("文章删除失败",e);
        }finally {
            return  resultVo;
        }
    }
}
