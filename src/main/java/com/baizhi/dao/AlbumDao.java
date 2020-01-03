package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //查所有
    List<Album> queryAlbum();

    //增加数据
    boolean insertAlbum(Album album);

    //删除数据
    boolean plDelete(String[] id);

    //修改数据
    boolean updateAlbum(Album album);

    //分页查询
    List<Album> pageAlbum(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询总条数
    Integer queryCount();
}
