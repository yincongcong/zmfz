package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    //分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPager(Integer rows, Integer page, String album_id) {
        /*
         * page  当前页
         * rows   数据
         * total   总条数
         * records   总页码
         * */
        //创建一个map集合
        HashMap<String, Object> map = new HashMap<>();
        //计算开始条数
        Integer start = (page - 1) * rows;
        //数据
        List<Chapter> chapters = chapterDao.queryPage(start, rows, album_id);
        //计算总条数
        Integer count = chapterDao.queryCount();
        //计算总页码
        Integer totalPage = count % rows == 0 ? count / rows : count / rows + 1;
        //将这些存进map集合中
        map.put("page", page);
        map.put("rows", chapters);
        map.put("total", count);
        map.put("records", totalPage);
        //将map集合返回
        return map;
    }

    //修改
    @Override
    public Map<String, String> updateChapter(Chapter chapter) {
        //创建一个map集合
        Map<String, String> map = new HashMap<>();
        //获取上传的路径
        String src = chapter.getSrc();
        //将路径分开
        String[] srcs = src.split("\\\\");
        //找到图片的路径
        String path = srcs[srcs.length - 1];
        if (chapter.getSrc().equals("")) {
            chapter.setSrc(null);
        } else {
            chapter.setSrc(path);
        }
        chapterDao.updateChapter(chapter);
        map.put("status", "添加成功");
        map.put("chapterId", chapter.getId());
        return map;
    }

    //删除
    @Override
    public void plDelete(String[] id) {
        chapterDao.plDeleteChapter(id);
    }

    //添加数据
    @Override
    public Map<String, String> insertChapter(Chapter chapter) {
        //创建一个map集合
        Map<String, String> map = new HashMap<>();
        //获取上传的路径
        String src = chapter.getSrc();
        //将路径分开
        String[] srcs = src.split("\\\\");
        //找到图片的路径
        String path = srcs[srcs.length - 1];
        //将音频的路径赋给chapter
        chapter.setSrc(path);
        //给id一个UUID
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insertChapter(chapter);
        map.put("status", "添加成功");
        map.put("chapterId", chapter.getId());
        //将map集合返回
        return map;
    }
}
