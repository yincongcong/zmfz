package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
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
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    //分页
    @RequestMapping("queryPage")
    public Map<String, Object> queryPage(Integer rows, Integer page) {
        Map<String, Object> map = bannerService.queryByPager(rows, page);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "====" + map1.getValue());
        }
        return map;
    }

    //文件上传
    @RequestMapping("upload")
    public void upload(MultipartFile img, String id, HttpSession session) throws IOException {
        System.out.println("图片的id" + id);
        System.out.println("文件名：" + img.getOriginalFilename());
        /*
         * 判断文件是否存在
         * */
        File file = new File(session.getServletContext().getRealPath("/upload"), img.getOriginalFilename());
        if (!file.exists()) {
            System.out.println("文件上传");
            img.transferTo(file);
        }
        //修改数据库中的图片路径
        Banner banner = new Banner();
        banner.setId(id);
        banner.setImg(img.getOriginalFilename());
    }

    //edit
    @RequestMapping("editBanner")
    @ResponseBody
    public Map<String, String> editBanner(Banner banner, String oper, String[] id) {
        Map<String, String> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = bannerService.insertBanner(banner);
            return map;
        } else if ("edit".equals(oper)) {
            bannerService.updateBanner(banner);
            System.out.println(banner.getId());
            return map;
        } else if ("del".equals(oper)) {
            System.out.println("-------------");
            System.out.println("action===" + banner.getId());
            bannerService.plDelete(id);
            return map;
        }
        return null;
    }
}
