package com.itheima.test;

import com.itheima.utils.MyTool;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class MyToolTest {


    @Test
    public void testEnd()
    {
        List<String> strings = null;
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFile() throws IOException, OpenXML4JException, XmlException {
        MyTool.writeTxt("d:/temp/temp.docx","阿瑟费阿瑟费撒旦法撒的发生大空记录\n撒旦教哦即");
    }

    @Test
    public void testDir()
    {
        MyTool.deleteDir(new File("D:\\我这一生\\研究生阶段！！！！！！！\\学习方面\\maven_project\\apache-tomcat-7.0.56\\webapps\\ROOT" +
                "\\upload/wsh/"));
    }

    @Test
    public void test() throws IOException {
        // 旧的文件或目录
        File oldName = new File("D:\\temp\\aaaa");
        // 新的文件或目录
        File newName = new File("D:\\temp\\teasfef");
        if (newName.exists()) {  //  确保新的文件名不存在
            throw new java.io.IOException("file exists");
        }
        if(oldName.renameTo(newName)) {
            System.out.println("已重命名");
        } else {
            System.out.println("Error");
        }
    }

}
