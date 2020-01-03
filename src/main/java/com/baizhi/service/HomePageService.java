package com.baizhi.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface HomePageService {
    //一级页面接口
    public Map<String, Object> selectHomePage(HttpServletRequest request, String type, String sub_type);

    //闻的详情页接口
    public Map<String, Object> selectWenPage(HttpServletRequest request);
}
