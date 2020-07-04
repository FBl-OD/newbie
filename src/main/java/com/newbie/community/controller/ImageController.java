package com.newbie.community.controller;

import com.newbie.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageController {

    @Value("${image-upload-path}")
    private String uploadPath;

    private static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostConstruct
    public void createUploadFolder(){
        File dir=new File(uploadPath);
        if(!dir.exists())
            dir.mkdirs();
    }

    /**
     * JSON data
     * {
     * success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
     * message : "提示的信息，上传成功或上传失败及错误信息等。",
     * url     : "图片地址"        // 上传成功时才返回
     * }
     */
    @RequestMapping(path = "/image", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadImage(@RequestParam("editormd-image-file") MultipartFile image) {
        Map<String, Object> map = new HashMap<>();
        if (image == null) {
            map.put("success",0);
            map.put("message","图片不能为空");
            return map;
        }
        String fileName = image.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            map.put("success",0);
            map.put("message","图片类型不支持");
            return map;
        }
        fileName = CommunityUtil.generateUUID();
        File dest = new File(uploadPath + "/" + fileName + suffix);
        map.put("success",1);
        map.put("message","图片上传成功");
        map.put("url", "/image/" + fileName + suffix);
        try {
            image.transferTo(dest);
        } catch (IOException e) {
            map.put("success",0);
            map.put("message","图片上传失败");
            map.put("url","");
            logger.error("上传图片失败: " + e.getMessage());
            throw new RuntimeException("上传图片失败：", e);
        }finally {
            return map;
        }
    }

    @RequestMapping(path = "/image/{fileName}", method = RequestMethod.GET)
    public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = uploadPath + "/" + fileName;
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取图片失败: " + e.getMessage());
        }
    }
}
