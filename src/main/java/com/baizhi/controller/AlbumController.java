package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //分页查询
    @RequestMapping("queryPage")
    public Map<String, Object> queryPage(Integer rows, Integer page) {
        Map<String, Object> map = albumService.queryByPager(rows, page);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "===" + map1.getValue());
        }
        return map;
    }

    //文件上传
    @RequestMapping("uploadAlbum")
    @ResponseBody
    public void uploadAlbum(MultipartFile img, String id, HttpSession session) {
        //获取文件上传的路径
        String realPath = session.getServletContext().getRealPath("/upload");
        File file = new File(realPath, img.getOriginalFilename());
        //判断文件是否存在
        if (!file.exists()) {
            System.out.println("======");
            //调用文件上传的方法
            try {
                img.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //修改文件路径
        Album album = new Album();
        album.setId(id);
        album.setImg(img.getOriginalFilename());
    }

    @RequestMapping("editAlbum")
    @ResponseBody
    public Map<String, String> editAlbum(Album album, String oper, String[] id) {
        System.out.println(album);
        //创建一个map集合
        Map<String, String> map = new HashMap<>();
        if ("add".equals(oper)) {
            //调用添加的方法
            map = albumService.insertAlbum(album);
            return map;
        } else if ("edit".equals(oper)) {
            //调用修改的方法
            map = albumService.updateAlbum(album);
            return map;
        } else if ("del".equals(oper)) {
            albumService.plDeleteAlbum(id);
            return null;
        }
        return null;
    }
}
