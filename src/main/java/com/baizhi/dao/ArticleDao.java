package com.baizhi.dao;

import com.baizhi.entity.Article;

import java.util.List;

public interface ArticleDao {
    //查所有
    List<Article> queryAllArticle();

    //通过id删除
    int deleteByPrimaryKey(String id);

    //添加数据
    int insert(Article record);

    //有选择的添加数据
    int insertSelective(Article record);

    //通过id查询
    Article selectByPrimaryKey(String id);

    //有选择的修改
    int updateByPrimaryKeySelective(Article record);

    //修改数据
    int updateByPrimaryKey(Article record);

    //批量删除
    void plDelete(String[] id);

    //分页查询
    List<Article> queryPage(Integer start, Integer rows);

    //查询总条数
    Integer pageCount();
}