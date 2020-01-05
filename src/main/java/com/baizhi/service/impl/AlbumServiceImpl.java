package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    //分页查询
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCache
    public Map<String, Object> queryByPager(Integer rows, Integer page) {
        /*
         * page  当前页
         * rows   数据
         * total   总条数
         * records   总页码
         * */
        //计算开始条数
        Integer start = (page - 1) * rows;
        //数据
        List<Album> albums = albumDao.pageAlbum(start, rows);
        //计算总条数
        Integer count = albumDao.queryCount();
        //计算总页码
        Integer totalPage = count % rows == 0 ? count / rows : count / rows + 1;
        //将这些存进map集合中
        //创建一个map集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", albums);
        map.put("total", totalPage);
        map.put("records", count);
        //将map集合返回
        return map;
    }

    //添加数据
    @Override
    @ClearCache
    public Map<String, String> insertAlbum(Album album) {
        //创建一个map集合
        HashMap<String, String> map = new HashMap<>();
        //获取页面传来的数据
        String img = album.getImg();
        //将获取的路径拆分
        String[] split = img.split("\\\\");
        //获取文件名
        String path = split[split.length - 1];
        //将文件名赋值给对象
        album.setImg(path);
        //给id赋值
        album.setId(UUID.randomUUID().toString());
        album.setCreate_date(new Date());
        //调用方法
        albumDao.insertAlbum(album);
        //将获取到的id存进map集合中
        map.put("albumId", album.getId());
        map.put("status", "200");
        //将map集合返回
        return map;
    }

    //修改数据
    @Override
    @ClearCache
    public Map<String, String> updateAlbum(Album album) {
        //创建一个map集合
        HashMap<String, String> map = new HashMap<>();
        //获取页面传来的数据
        String img = album.getImg();
        //将获取的路径拆分
        String[] split = img.split("\\\\");
        //获取文件名
        String path = split[split.length - 1];
        //判断是否修改图片
        if ("".equals(album.getImg())) {
            album.setImg(null);
        } else {
            album.setImg(path);
        }
        //调用修改的方法
        albumDao.updateAlbum(album);
        //将map集合返回
        return map;
    }

    //批量删除
    @Override
    @ClearCache
    public void plDeleteAlbum(String[] id) {
        //调用方法
        albumDao.plDelete(id);
    }

    //查所有
    @Override
    public List<Album> queryAllAlbum() {
        //调用方法
        List<Album> albums = albumDao.queryAlbum();
        return albums;
    }
}
