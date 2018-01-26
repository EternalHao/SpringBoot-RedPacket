package com.shangfu.springbootfilghting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class UpController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    //处理文件上传
    @RequestMapping(value="/upfile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) {
        if(file.isEmpty()){
            return "文件为空";
        }
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        //String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "./src/main/resources/up/";
        System.out.println(filePath);
        try {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        //返回json
        return "上传成功";
    }
}
