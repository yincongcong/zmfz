package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页查询    参数rows    每页多少条数据   page   当前页
    public Map<String, Object> queryByPager(Integer rows, Integer page);

    //添加数据
    Map<String, String> insertAlbum(Album album);

    //修改数据
    Map<String, String> updateAlbum(Album album);

    //批量删除
    void plDeleteAlbum(String[] id);

    //查所有
    List<Album> queryAllAlbum();
}
