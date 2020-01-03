package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> selectHomePage(HttpServletRequest request, String type, String sub_type) {
        //调用方法
        List<Banner> banners = bannerDao.queryAllBanner();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Banner banner : banners) {
            HashMap<String, Object> map1 = new HashMap<>();
            try {
                String scheme = request.getScheme();//http
                InetAddress localHost = InetAddress.getLocalHost();//localhost
                String localhost = localHost.toString().split("/")[1];
                int port = request.getServerPort();//port
                String contextPath = request.getContextPath();//项目名
                String url = scheme + "://" + localhost + ":" + port + contextPath + "/upload/" + banner.getImg();
                map1.put("thumbnail", url);
                map1.put("desc", banner.getTitle());
                map1.put("id", banner.getId());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            list.add(map1);
        }
        List<Album> albums = albumDao.queryAlbum();
        List<Map<String, Object>> list1 = new ArrayList<>();
        for (Album album : albums) {
            HashMap<String, Object> map2 = new HashMap<>();
            String scheme = request.getScheme();//http
            InetAddress localHost = null;//localhost
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String localhost = localHost.toString().split("/")[1];
            int port = request.getServerPort();//port
            String contextPath = request.getContextPath();//项目名
            String url = scheme + "://" + localhost + ":" + port + contextPath + "/upload/" + album.getImg();
            map2.put("thumbnail", url);
            map2.put("title", album.getTitle());
            map2.put("type", "0");
            map2.put("set_count", album.getCount());
            map2.put("create_date", album.getCreate_date().toString());
            list1.add(map2);
        }
        Map<String, Object> map = new HashMap<>();
        if (type.equals("all")) {
            map.put("banner", list);
        }
        map.put("body", list1);
        return map;
    }

    @Override
    public Map<String, Object> selectWenPage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Album> albums = albumDao.queryAlbum();
        List<Object> list = new ArrayList<>();
        List<Chapter> chapters = chapterDao.queryChapter();
        for (Chapter chapter : chapters) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("title", chapter.getTitle());
            String scheme = request.getScheme();//http
            InetAddress localHost = null;//localhost
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String localhost = localHost.toString().split("/")[1];
            int port = request.getServerPort();//port
            String contextPath = request.getContextPath();//项目名
            String url = scheme + "://" + localhost + ":" + port + contextPath + "/music/" + chapter.getSrc();
            map1.put("download_url", url);
            map1.put("size", chapter.getSize());
            map1.put("duration", chapter.getDuration());
            list.add(map1);
        }
        Map<Object, Object> map2 = new HashMap<>();
        for (Album album : albums) {
            String scheme = request.getScheme();//http
            InetAddress localHost = null;//localhost
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String localhost = localHost.toString().split("/")[1];
            int port = request.getServerPort();//port
            String contextPath = request.getContextPath();//项目名
            String url = scheme + "://" + localhost + ":" + port + contextPath + "/upload/" + album.getImg();
            map2.put("thumbnail", url);
            map2.put("title", album.getTitle());
            map2.put("score", album.getScore());
            map2.put("author", album.getAuthor());
            map2.put("broadcast", album.getBroadcaster());
            map2.put("set_count", album.getCount());
            map2.put("brief", album.getBrief());
            map2.put("create_date", album.getCreate_date().toString());
        }
        map.put("introduction", map2);
        map.put("list", list);
        return map;
    }
}
