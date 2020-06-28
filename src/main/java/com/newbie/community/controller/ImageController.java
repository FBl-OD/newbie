package com.newbie.community.controller;

import com.newbie.community.Vo.ResultVo;
import com.newbie.community.util.CommunityConst;
import com.newbie.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class ImageController implements CommunityConst {

    @Value("${uploadPath}")
    private String uploadPath;

    private static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(path = "/image", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo uploadImage(MultipartFile image) {
        ResultVo vo = new ResultVo();
        if (image == null) {
            vo.setCode(CommunityConst.CLIENT_ERROR);
            vo.setMsg("图片不能为空");
            return vo;
        }
        String fileName = image.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            vo.setCode(CommunityConst.CLIENT_ERROR);
            vo.setMsg("图片类型不支持");
            return vo;
        }
        fileName = CommunityUtil.generateUUID();
        File dest = new File(uploadPath + "/" + fileName + suffix);
        try {
            image.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传图片失败: " + e.getMessage());
            throw new RuntimeException("上传图片失败：", e);
        }
        vo.setCode(CommunityConst.OK);
        HashMap<String, Object> map = new HashMap<>();
        map.put("path", "/image/" + fileName + suffix);
        vo.setData(map);
        vo.setMsg("图片上传成功");
        return vo;
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
