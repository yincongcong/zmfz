package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryPageArticle(Integer page, Integer rows) {
        //计算开始条数
        Integer start = (page - 1) * rows;
        //数据
        List<Article> articles = articleDao.queryPage(start, rows);
        //计算总条数
        Integer count = articleDao.pageCount();
        System.out.println(count);
        //计算总页码
        Integer totalPage = count % rows == 0 ? count / rows : count / rows + 1;
        System.out.println(totalPage);
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", articles);
        map.put("total", totalPage);
        map.put("records", count);
        return map;
    }

    //添加数据
    @Override
    @Transactional
    @ClearCache
    public void insertArticle(Article article) {
        //调用添加的方法
        article.setId(UUID.randomUUID().toString());
        int i = articleDao.insert(article);

        if (i != 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
    }

    //修改数据
    @Override
    @ClearCache
    public void updateArticle(Article article) {
        //调用方法

        int i = articleDao.updateByPrimaryKeySelective(article);
        System.out.println("----------------" + article);
        System.out.println(i);
        if (i != 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
    }

    //批量删除
    @Override
    @ClearCache
    public void plDelete(String[] id) {
        articleDao.plDelete(id);
    }

    //查所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCache
    public List<Article> queryAllArticle() {
        List<Article> articles = articleDao.queryAllArticle();
        return articles;
    }
}
