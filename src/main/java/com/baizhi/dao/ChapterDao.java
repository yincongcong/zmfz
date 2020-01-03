package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //查所有
    List<Chapter> queryChapter();

    //添加数据
    void insertChapter(Chapter chapter);

    //批量删除
    void plDeleteChapter(String[] id);

    //修改数据
    void updateChapter(Chapter chapter);

    //分页查询
    List<Chapter> queryPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("album_id") String album_id);

    //查询总条数
    Integer queryCount();
}
