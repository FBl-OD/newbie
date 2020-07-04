package com.newbie.community.controller;

import com.newbie.community.Vo.PageVo;
import com.newbie.community.Vo.ResultVo;
import com.newbie.community.entity.Resource;
import com.newbie.community.entity.User;
import com.newbie.community.service.FileService;
import com.newbie.community.service.UserService;
import com.newbie.community.util.CommunityConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ResourceController implements CommunityConst {

    private static final Logger logger= LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Value("${file-upload-path}")
    private String uploadPath;

    @RequestMapping(path = "/resource", method = RequestMethod.GET)
    public String getFilePage(Model model, PageVo pageVo) {
        pageVo.setPath("/resource");
        pageVo.setRows(fileService.queryCount(0));
        List<Resource> resources = fileService.queryAll(0, pageVo.getOffset(), pageVo.getLimit());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Resource resource : resources) {
            Map<String, Object> map = new HashMap<>();
            User user = userService.queryById(resource.getUserId());
            map.put("file", resource);
            map.put("user", user);
            list.add(map);
        }
        model.addAttribute("files", list);
        return "/site/resource";
    }

    @RequestMapping(path = "/resource",method = RequestMethod.POST)
    @ResponseBody
    public ResultVo addFile(MultipartFile file){
        ResultVo resultVo = new ResultVo();
        if(file==null){
            resultVo.setCode(CLIENT_ERROR);
            resultVo.setMsg("文件不能为空");
            return resultVo;
        }
        String fileName=file.getOriginalFilename();

        Resource f = new Resource();
        f.setUserId(101);
        f.setTitle(fileName);
        f.setStatus(0);
        f.setCreateTime(new Date());
        fileService.add(f);

        String parentDir= String.valueOf(f.getId());
        File dest=new File(uploadPath+"/"+parentDir+"/"+fileName);
        try{
            file.transferTo(dest);
        }catch (IOException e){
            logger.error("文件上传失败："+e.getMessage());
            //统一异常处理
            throw new RuntimeException("文件上传失败",e);
        }
        resultVo.setCode(OK);
        resultVo.setMsg("文件上传成功");
        return resultVo;
    }
}
