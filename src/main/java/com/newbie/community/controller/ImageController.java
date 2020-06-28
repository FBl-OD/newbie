package com.newbie.community.controller;

import com.newbie.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageController {

    @Value("${uploadPath}")
    private String uploadPath;

    private static Logger logger= LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(path = "/image", method = RequestMethod.POST)
    public String uploadImage(MultipartFile headerImage) {
        if (headerImage == null) {

        }
        String fileName = headerImage.getOriginalFilename();
        // 生成随机文件名
        fileName = CommunityUtil.generateUUID();
        // 文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传图片失败: " + e.getMessage());
            throw new RuntimeException("上传图片失败,服务器发生异常!", e);
        }
        return null;

    }

//    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
//    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
//        // 服务器存放路径
//        fileName = uploadPath + "/" + fileName;
//        // 文件后缀
//        String suffix = fileName.substring(fileName.lastIndexOf("."));
//        // 响应图片
//        response.setContentType("image/" + suffix);
//        try (
//                FileInputStream fis = new FileInputStream(fileName);
//                OutputStream os = response.getOutputStream();
//        ) {
//            byte[] buffer = new byte[1024];
//            int b = 0;
//            while ((b = fis.read(buffer)) != -1) {
//                os.write(buffer, 0, b);
//            }
//        } catch (IOException e) {
//            logger.error("读取头像失败: " + e.getMessage());
//        }
//    }

}
