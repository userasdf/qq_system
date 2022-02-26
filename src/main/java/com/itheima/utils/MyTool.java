package com.itheima.utils;

import org.apache.poi.POITextExtractor;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyTool {

    //获取当前日期
    public static String getTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    //获取当前目录下面的内容
    public static String getContentOfDir(String dir)
    {
        File file = new File(dir);
        //如果文件不存在或文件夹里面是空记录
        if(!file.exists()||file.listFiles().length==0)
            return "空记录";
        //如果不是空记录
        String res = "";//要返回的字符串
        File[] listFiles = file.listFiles();
        for (File listFile : listFiles) {
            res += listFile.getName()+","+listFile.isFile()+";";
        }
        res = res.substring(0,res.length()-1);//去掉最后一个分号
        return res;
    }

    //删除某个目录及其该目录下面的所有内容
    public static void deleteDir(File path)
    {
        if(path.isFile())
            path.delete();
        else
        {
            File[] items = path.listFiles();
            for (File item : items) {
                deleteDir(item);
            }
            path.delete();
        }
    }

    //读取文件
    public static String readTxt(String path) throws IOException{
        String res = "";//要返回的字符串
        FileInputStream inputStream = new FileInputStream(path);//文件输入流
        if(path.endsWith(".txt"))//读取txt文件
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null)
            {
                res += line+"\n";
                line = reader.readLine();
            }
            reader.close();
            inputStreamReader.close();
        }
        else if(path.endsWith(".docx")||path.endsWith(".doc"))
        {
            try {
                XWPFDocument document = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                res = extractor.getText();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                res = "这里面没有记录或出现了异常！";
            }
        }

        inputStream.close();
        return res;
    }

    //写入文件
    public static void writeTxt(String path,String content) throws IOException {
        //文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(path, false);
        if(path.endsWith(".txt"))
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write(content);
            writer.close();
            outputStreamWriter.close();
        }
        else if(path.endsWith(".doc")||path.endsWith(".docx"))
        {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(content);
            //输出流
            BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream,1024);
            document.write(stream);
            stream.close();
        }
        fileOutputStream.close();
    }

}
