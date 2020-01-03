package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //分页查询    参数rows    每页多少条数据   page   当前页
    Map<String, Object> queryByPager(Integer rows, Integer page, String album_id);

    //添加数据
    Map<String, String> insertChapter(Chapter chapter);

    //修改数据
    Map<String, String> updateChapter(Chapter chapter);

    //批量删除
    void plDelete(String[] id);
}
