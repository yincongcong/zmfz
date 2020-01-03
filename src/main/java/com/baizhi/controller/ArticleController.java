package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("queryPageArticle")
    //分页查询
    @ResponseBody
    public Map<String, Object> queryPageArticle(Integer page, Integer rows) {
        //调用方法
        Map<String, Object> map = articleService.queryPageArticle(page, rows);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "----" + map1.getValue());
        }
        return map;
    }

    //添加数据
    @ResponseBody
    @RequestMapping("insertArticle")
    public String insertArticle(Article article) {
        //调用方法
        articleService.insertArticle(article);
        return null;
    }

    //修改
    @ResponseBody
    @RequestMapping("updateArticle")
    public String updateArticle(Article article) {
        System.out.println("===" + article);
        //调用方法
        articleService.updateArticle(article);
        return null;
    }

    //增删改操作
    @RequestMapping("editArticle")
    @ResponseBody
    public void editArticle(String oper, String[] id) {
        if ("del".equals(oper)) {
            articleService.plDelete(id);
        }
    }

    //文件上传
    @RequestMapping("uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(MultipartFile img, HttpSession session, HttpServletRequest request) {
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();
        //获取文件上传的路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //创建一个文件
        File file = new File(realPath);
        //判断文件是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        //获取上传文件的文件名
        String filename = img.getOriginalFilename();
        //添加时间戳
        String name = new Date().getTime() + "_" + filename;
        //上传文件
        try {
            img.transferTo(new File(realPath, name));
            /*
             *
             * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
             * {"error":0, "url":"http://localhost:80/cmfz/upload/img/xxx.png" }
             * */
            map.put("error", 0);
            //获取文件路径
            String scheme = request.getScheme();//http
            InetAddress localHost = InetAddress.getLocalHost();//localHost
            //PC-20190718ZLAM/192.168.1.156
            //将这个拆分
            String localhost = localHost.toString().split("/")[1];
            int port = request.getServerPort();//port
            String contextPath = request.getContextPath();//项目名
            //将这些拼接
            String url = scheme + "://" + localhost + ":" + port + contextPath + "/upload/img/" + name;
            //将URL存进map集合中
            map.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
    *
    * {
    "moveup_dir_path": "",
    "current_dir_path": "",
    "current_url": "/ke4/php/../attached/",
    "total_count": 5,
    "file_list": [
        {
            "is_dir": false,
            "has_file": false,
            "filesize": 208736,
            "dir_path": "",
            "is_photo": true,
            "filetype": "jpg",
            "filename": "1241601537255682809.jpg",
            "datetime": "2018-06-06 00:36:39"
        }
     ]
}
    * */
    //在图片空间显示
    @RequestMapping("getAllImgs")
    @ResponseBody
    public Map<String, Object> getAllImgs(HttpSession session, HttpServletRequest request) {
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();
        //拿到所有在图片空间展示的图片的路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        String[] imgs = file.list();
        //创建一个list集合
        ArrayList<Object> list = new ArrayList<>();
        //遍历imgs添加到map集合中
        for (String img : imgs) {
            //在创建一个map集合
            Map<String, Object> map1 = new HashMap<>();
            map1.put("is_dir", false);
            map1.put("has_file", false);
            File file1 = new File(realPath, img);
            long length = file1.length();
            map1.put("filesize", length);
            map1.put("dir_path", "");
            map1.put("is_photo", true);
            //返回文件的后缀
            String extension = FilenameUtils.getExtension(img);
            map1.put("filetype", extension);
            map1.put("filename", img);
            //文件名有时间戳  将文件名拆开  获取上传时间
            String src = img.split("_")[0];
            Long aLong = Long.valueOf(src);
            //得到时间
            Date date = new Date(aLong);
            //将时间转化为String类型
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            map1.put("datetime", format);
            //将map集合添加至list集合中
            list.add(map1);
        }
        map.put("file_list", list);
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        /*
         * http://localhost:80/cmfz/upload/img
         * */
        try {
            String scheme = request.getScheme();//http
            InetAddress localHost = InetAddress.getLocalHost();//localhost
            //PC-20190718ZLAM/192.168.1.156
            //拆分
            String s = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();//port
            String contextPath = request.getContextPath();//项目名
            String s1 = scheme + "://" + s + ":" + serverPort + contextPath + "/upload/img/";
            //将路径存进map集合中
            map.put("current_url", s1);
            map.put("total_count", imgs.length);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }
}
