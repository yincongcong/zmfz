package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //分页
    @RequestMapping("queryPageChapter")
    public Map<String, Object> queryPageChapter(Integer rows, Integer page, String album_id) {
        Map<String, Object> map = chapterService.queryByPager(rows, page, album_id);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "====" + map1.getValue());
        }
        return map;
    }

    //文件上传
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile src, String id, HttpSession session) {
        System.out.println("音频的id" + id);
        System.out.println("文件名：" + src.getOriginalFilename());
        /*
         * 判断文件是否存在
         * */
        File file = new File(session.getServletContext().getRealPath("/music"), src.getOriginalFilename());
        if (!file.exists()) {
            System.out.println("文件上传");
            try {
                src.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         * 获取文件的时长
         * */
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(file);//这里传入的是文件的对象
            ls = m.getDuration() / 1000;//得到一个long类型的时长
        } catch (EncoderException e) {
            e.printStackTrace();
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        //获取文件大小
        double size = file.length() / 1024.0 / 1024;
        size = (double) Math.round(size * 100) / 100;
        String size1 = String.valueOf(size) + "MB";
        //修改数据库中的图片路径

        Chapter chapter = new Chapter();
        if (!src.getOriginalFilename().equals("")) {
            chapter.setDuration(String.valueOf(ls / 60) + ":" + String.valueOf(ls % 60));
            chapter.setId(id);
            chapter.setSize(size1);
            chapter.setSrc(src.getOriginalFilename());
        }
        chapterService.updateChapter(chapter);
        System.out.println("qqqqqqqqqqqqqqqqqqqq" + chapter);
    }

    @RequestMapping("editChapter")
    public Map<String, String> editChapter(Chapter chapter, String oper, String[] id) {
        Map<String, String> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = chapterService.insertChapter(chapter);
            return map;
        } else if ("edit".equals(oper)) {
            map = chapterService.updateChapter(chapter);
            return map;
        } else if ("del".equals(oper)) {
            chapterService.plDelete(id);
            return null;
        }
        return null;
    }

    //文件下载
    @RequestMapping("downFile")
    @ResponseBody
    public String downFile(String src, HttpSession session, HttpServletResponse response) {
        //接收数据    文件标识  文件名
        //调用业务    从服务器中获取文件    然后响应给客户端
        //获取文件？   文件路径   通过相对路径获取绝对路径   IO流输出
        String[] split = src.split("/");
        String fileName = split[split.length - 1];
        String realPath = session.getServletContext().getRealPath("/music");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath, fileName));
            //设置文件下载的方式
            String encode = URLEncoder.encode(fileName, "UTF-8");
            //设置响应类型
            String extension = FilenameUtils.getExtension(fileName);
            System.out.println("后缀名为：" + extension);
            //在通过后缀名获取MIMA类型   没有“.”
            String mimeType = session.getServletContext().getMimeType("." + extension);
            System.out.println("MIMA类型为：" + mimeType);
            //设置响应类型 响应的文件类型    动态获取
            response.setHeader("content-disposition", "attachment;fileName=" + encode);
            //通过响应输出流给客户端打印数据
            ServletOutputStream outputStream = response.getOutputStream();
            //文件传输    读取过程中给客户端响应数据
            byte[] bytes = new byte[2048];
            while (true) {
                //返回值代表读取数据的个数   如果到达文件末尾   返回-1
                int i = fileInputStream.read(bytes, 0, bytes.length);
                if (i == -1) break;
                //向外响应
                outputStream.write(bytes, 0, 1);
            }
            //关闭资源
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //不用跳转页面
        return null;
    }
}
