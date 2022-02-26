package com.itheima.controller;

import com.itheima.entity.User;
import com.itheima.utils.MyTool;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    //进入云文件系统页面
    @RequestMapping("/system")
    public String system(String tempDir,Model model,HttpServletRequest request)
    {
        if(tempDir==null)
            tempDir = "/";
        model.addAttribute("tempDir",tempDir);
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取用户根目录
        String root = request.getSession().getServletContext().getRealPath("/upload")+"/"+username;
        //如果用户根目录不存在，则创建根目录
        File file = new File(root);
        if(!file.exists())
            file.mkdirs();
        return "file_management";
    }

    //文件上传
    @RequestMapping("/upload")
    public String upload(String tempDir,String fileName, MultipartFile uploadFile,
                         HttpServletRequest request, Model model) throws IOException {

        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取当前目录，处理中文乱码
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        //获取当前路径，若不存在，则创建
        String root = request.getSession().getServletContext().getRealPath("/upload")+"/"+username+"/"+tempDir;
        //获取用户输入的文件名字
        fileName = fileName.trim();//首先截取字符串两端的空格
        if("".equals(fileName))//如果用户没有输入文件名字，就使用文件原始名字
            fileName = uploadFile.getOriginalFilename();
        String filePath = root+"/"+fileName;
        //上传文件
        uploadFile.transferTo(new File(filePath));

        //将dir返回
        model.addAttribute("tempDir",tempDir);
        return "file_management";
    }

    //显示当前文件夹中的内容
    @RequestMapping(value = "/loadContent",produces = "text/html;charset=utf8")
    @ResponseBody
    public String loadContent(String tempDir,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //处理中文乱码，获取文件夹名字
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        //获取文件夹路径
        String path = request.getSession().getServletContext().getRealPath("upload/")+"/"+username+"/"+tempDir;
        //返回当前目录下面的文件及文件夹
        return MyTool.getContentOfDir(path);
    }


    //新建文件夹
    @RequestMapping(value = "/createFolder",produces = "text/html;charset=utf8")
    @ResponseBody
    public void createFolder(String tempDir,String newFolder,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //处理中文乱码，获取文件夹名字
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        newFolder = new String(newFolder.getBytes("iso-8859-1"),"utf8");
        //获取文件夹路径
        String path = request.getSession().getServletContext().getRealPath("upload/")+"/"+username+"/"+tempDir+"/"+newFolder;
        //创建文件夹
        new File(path).mkdirs();
    }

    //新建记事本
    @RequestMapping("/createTxt")
    @ResponseBody
    public void createTxt(String tempDir,String fileName,HttpServletRequest request) throws IOException {
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //处理中文乱码，获取文件夹名字
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        fileName = new String(fileName.getBytes("iso-8859-1"),"utf8");
        //获取文件夹路径
        String path = request.getSession().getServletContext().getRealPath("upload/")+"/"+username+"/"+tempDir+"/"+fileName;
        //创建文件
        new File(path).createNewFile();
    }

    //文件下载
    @RequestMapping("/down")
    @ResponseBody
    public void down(String tempDir,String filename,HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取文件名，并处理中文乱码
        filename = new String(filename.getBytes("iso-8859-1"),"utf8");
        //获取当前目录，并处理中文乱码
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //模拟文件路径
        String str = request.getSession().getServletContext()
                .getRealPath("upload")+"/"+username+"/"+tempDir+"/"+filename;
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
        }
        out.flush();
        out.close();
    }

    //文件删除
    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String path,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取根路径
        String root = request.getSession().getServletContext().getRealPath("upload/")+"/"+username;
        //处理中文乱码
        path = new String(path.getBytes("iso-8859-1"),"utf8");
        //获取最终要删除的路径
        path = root + path;
        File file = new File(path);
        MyTool.deleteDir(file);
    }

    //文件重命名
    @RequestMapping("/rename")
    @ResponseBody
    public void rename(String tempDir,String oldName,String newName,HttpServletRequest request) throws UnsupportedEncodingException {
        //解决中文乱码
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        oldName = new String(oldName.getBytes("iso-8859-1"),"utf8");
        newName = new String(newName.getBytes("iso-8859-1"),"utf8");
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取根路径
        String root = request.getSession().getServletContext().getRealPath("upload/")
                +"/"+username+"/"+tempDir;
        File file = new File(root+"/"+oldName);
        file.renameTo(new File(root+"/"+newName));
    }

    //读取.txt文件
    @RequestMapping(value = "/readTxt",produces = "text/html;charset=utf8")
    public String readTxt(String tempDir,String fileName,HttpServletRequest request,Model model) throws IOException, OpenXML4JException, XmlException {
        //处理中文乱码
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        fileName = new String(fileName.getBytes("iso-8859-1"),"utf8");
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取文件路径
        String path = request.getSession().getServletContext().getRealPath("upload/")
                +"/"+username+"/"+tempDir+"/"+fileName;
        String content = MyTool.readTxt(path);//读取的文本
        model.addAttribute("content",content);//存储读取的文本
        model.addAttribute("tempDir",tempDir);//存储当前目录
        model.addAttribute("fileName",fileName);//存储当前目录
        return "readTxt";
    }

    //保存文件
    @RequestMapping("/saveTxt")
    @ResponseBody
    public void saveTxt(String content,String tempDir,String fileName,HttpServletRequest request) throws IOException {
        //处理中文乱码
        tempDir = new String(tempDir.getBytes("iso-8859-1"),"utf8");
        fileName = new String(fileName.getBytes("iso-8859-1"),"utf8");
        content = new String(content.getBytes("iso-8859-1"),"utf8");
        //获取用户名
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        //获取文件路径
        String path = request.getSession().getServletContext().getRealPath("upload/")
                +"/"+username+"/"+tempDir+"/"+fileName;
        //写入文件
        MyTool.writeTxt(path,content);
    }
}
