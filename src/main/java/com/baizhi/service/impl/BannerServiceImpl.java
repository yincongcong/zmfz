package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    //查所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> queryAllBanner() {
        List<Banner> banners = bannerDao.queryAllBanner();
        return banners;
    }

    //添加数据
    @Override
    @ClearCache
    public Map<String, String> insertBanner(Banner banner) {
        //创建一个map集合
        HashMap<String, String> map = new HashMap<>();
        //获取页面上传来的路径
        String img = banner.getImg();
        //将路径拆分
        String[] imgs = img.split("\\\\");
        //找到图片的名字
        String path = imgs[imgs.length - 1];
        //将图片的路径赋给banner
        banner.setImg(path);
        //给一个UUID
        banner.setId(UUID.randomUUID().toString());
        //图片上传的时间
        banner.setCreate_date(new Date());
        //调用dao的方法
        bannerDao.insertBanner(banner);
        //将要上传图片的id存进map集合中
        map.put("bannerId", banner.getId());
        //将页面的状态存进map集合中
        map.put("status", "200");
        //将map集合返回
        return map;
    }

    //批量删除
    @Override
    @ClearCache
    public String plDelete(String[] ids) {
        bannerDao.plDelete(ids);
        return "删除成功";
    }

    //修改数据
    @Override
    @ClearCache
    public Map<String, String> updateBanner(Banner banner) {
        //创建一个map集合
        HashMap<String, String> map = new HashMap<>();
        //获取页面上传来的路径
        String img = banner.getImg();
        //将路径拆分
        String[] imgs = img.split("\\\\");
        //找到图片的名字
        String path = imgs[imgs.length - 1];
        //判断页面有没有修改图片
        //如果页面传来的是null
        if (banner.getImg().equals("")) {
            //将图片路径赋值为null
            banner.setImg(null);
        } else {
            //如果要修改图片
            banner.setCreate_date(new Date());
            //将图片的路径赋给banner
            banner.setImg(path);
        }
        bannerDao.updateBanner(banner);
        map.put("bannerId", banner.getId());
        map.put("status", "200");
        return map;
    }

    //分页查询
    @Override
    @AddCache
    public Map<String, Object> queryByPager(Integer rows, Integer page) {
        /*
         * 返回参数
         *page  当前页
         * rows  数据
         * total   总页数
         * records  总条数
         * */
        //计算开始条数
        Integer start = (page - 1) * rows;
        //数据
        List<Banner> banners = bannerDao.pageBanner(start, rows);
        //总条数
        Integer counts = bannerDao.queryCounts();
        //计算总页码
        Integer totalPage = counts % rows == 0 ? counts / rows : counts / rows + 1;
        //创建一个Map集合
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", banners);
        map.put("total", totalPage);
        map.put("records", counts);
        return map;
    }
}
