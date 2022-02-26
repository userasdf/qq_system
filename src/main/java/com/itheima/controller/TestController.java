package com.itheima.controller;

import com.itheima.entity.User;
import com.itheima.service.RelationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private RelationService relationService;

    @RequestMapping(value = "/test",produces = "text/html;charset=utf8")
    @ResponseBody
    public void test(MultipartFile filename, HttpServletRequest request) throws IOException {
        String str = request.getSession().getServletContext().getRealPath("/")+"upload";
        File file = new File(str);
        if(!file.exists())
            file.mkdir();
        filename.transferTo(new File(str+"\\"+filename.getOriginalFilename()));
    }

    @RequestMapping(value = "/ajax",produces = "text/html;charset=utf8")
    @ResponseBody
    public String ajax(HttpServletRequest request)
    {
        String str = request.getSession().getServletContext().getRealPath("upload/");
        File file = new File(str);
        if(!file.exists())
            return "nulllll";
        String res = "";
        File[] listFiles = file.listFiles();
        for (File listFile : listFiles) {
            res += listFile.getName()+",";
        }
        if(res.length()>0)
            res = res.substring(0,res.length()-1);
        return res;
    }

    //文件下载
    @RequestMapping("/down")
    public void down(String filename,HttpServletRequest request,HttpServletResponse response) throws Exception{
        //处理文件名的中文乱码
        filename = new String(filename.getBytes("iso-8859-1"),"utf8");
        //模拟文件路径
        String str = request.getSession().getServletContext().getRealPath("upload")+"/"+filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(str)));
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

}
