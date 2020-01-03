package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //查所有
    public List<Banner> queryAllBanner();

    //添加数据
    public Map<String, String> insertBanner(Banner banner);

    //批量删除
    public String plDelete(String[] ids);

    //修改数据
    public Map<String, String> updateBanner(Banner banner);

    //分页查询    参数rows    每页多少条数据   page   当前页
    public Map<String, Object> queryByPager(Integer rows, Integer page);

}
