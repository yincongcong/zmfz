package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //查所有
    public List<Banner> queryAllBanner();

    //添加数据
    public void insertBanner(Banner banner);

    //批量删除
    public void plDelete(String[] ids);

    //修改数据
    public void updateBanner(Banner banner);

    //分页   参数1：起始页码     参数2：每页展示的条数
    public List<Banner> pageBanner(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询总条数
    public Integer queryCounts();
}
