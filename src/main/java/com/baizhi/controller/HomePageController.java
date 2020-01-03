package com.baizhi.controller;

import com.baizhi.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@ResponseBody
public class HomePageController {
    @Autowired
    private HomePageService homePageService;

    @RequestMapping("first_page")
    @ResponseBody
    public Map<String, Object> first_page(HttpServletRequest request, String type, String sub_type) {
        //调用方法
        Map<String, Object> map = homePageService.selectHomePage(request, type, sub_type);
        return map;
    }

    @RequestMapping("wen")
    @ResponseBody
    public Map<String, Object> wen(HttpServletRequest request) {
        Map<String, Object> map = homePageService.selectWenPage(request);
        return map;
    }
}
