package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map<String, Object> queryPageArticle(Integer page, Integer rows);

    //添加数据
    public void insertArticle(Article article);

    //修改数据
    public void updateArticle(Article article);

    //删除数据
    public void plDelete(String[] id);

    //查所有
    public List<Article> queryAllArticle();

}
