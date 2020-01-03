import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.ZmfzApplication;
import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ZmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestClass {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    public void test1() {
        /*List<Album> albums = albumDao.queryAlbum();
        for (Album album : albums) {
            System.out.println(album);
        }*/
        /*Album album = new Album();
        album.setId(UUID.randomUUID().toString());
        boolean b = albumDao.insertAlbum(album);
        System.out.println(b);*/
       /* Album album = new Album();
        album.setId("a38c78a4-7a31-4469-ab11-0758330d0332");
        album.setStatus("冻结");
        boolean b = albumDao.updateAlbum(album);
        System.out.println(b);*/
        /*List<Chapter> chapters = chapterDao.queryChapter();
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }*/
        Map<String, Object> map = albumService.queryByPager(1, 1);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "=====" + map1.getValue());
        }
        /*List<Album> albums = albumDao.pageAlbum(0, 1);
        for (Album album : albums) {
            System.out.println(album);
        }*/
    }

    @Test
    public void test2() {
        /*List<Article> articles = articleDao.queryPage(0, 2);
        for (Article article : articles) {
            System.out.println(article);
        }*/
        Map<String, Object> map = articleService.queryPageArticle(1, 2);
        for (Map.Entry<String, Object> map1 : map.entrySet()) {
            System.out.println(map1.getKey() + "---" + map1.getValue());
        }
    }

    //导出
    @Test
    public void test() throws IOException {
        //创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFFont font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        font.setBold(true);
        //创建样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建工作簿
        HSSFSheet sheet = workbook.createSheet();
        //设置长度
        sheet.setColumnWidth(3, 10 * 256);
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] titles = {"用户id", "用户名", "用户密码"};
        //创建单元格
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }
        //创建能内容行
        List<Admin> admins = adminDao.queryAll();
        for (int i = 0; i < admins.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(admins.get(i).getId());
            row1.createCell(1).setCellValue(admins.get(i).getUsername());
            row1.createCell(2).setCellValue(admins.get(i).getPassword());
        }
        //指定路径
        workbook.write(new File("D:/admin.xls"));
        workbook.close();
    }

    //导入
    @Test
    public void test4() throws Exception {
        //创建excle文件   并且  读入对应的excle
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("D:/admin.xls"));
        System.out.println(workbook);
        //获得sheet    工作簿
        HSSFSheet sheet = workbook.getSheet("管理员信息");
        System.out.println(sheet);
        //获得最后一行的下标
        int lastRowNum = sheet.getLastRowNum();
        System.out.println(lastRowNum);
        //创建一个list集合存储读入的数据
        List<Admin> list = new ArrayList<>();
        //遍历
        for (int i = 1; i <= lastRowNum; i++) {
            //获取信息
            String id = sheet.getRow(i).getCell(0).getStringCellValue();
            String username = sheet.getRow(i).getCell(1).getStringCellValue();
            String password = sheet.getRow(i).getCell(2).getStringCellValue();
            Admin admin = new Admin(id, username, password, null);
            list.add(admin);
        }
        for (Admin admin : list) {
            System.out.println(admin);
        }
    }


    //EasypoiOut导出
    @Test
    public void test6() throws Exception {
        //查所有
        List<Banner> banners = bannerService.queryAllBanner();
        for (Banner banner : banners) {
            /*E:\180班\第三阶段框架阶段\IDEACode\zmfz\src\main\webapp\img\*/
            banner.setImg("E:\\180班\\第三阶段框架阶段\\IDEACode\\zmfz\\src\\main\\webapp\\img\\" + banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("大图轮播管理", "大图轮播"),
                Banner.class, banners);
        workbook.write(new FileOutputStream("D:/banner.xls"));
        workbook.close();
    }

    @Test
    public void testDay() {
        User user = new User();
        user.setId("38");
        user.setCreate_date(new Date());
        userDao.insert(user);
        List<DayDto> list = userService.queryDay();
        ArrayList<Integer> list1 = new ArrayList<>();
        for (DayDto dayDto : list) {
            Integer counts = dayDto.getCounts();
            list1.add(counts);
        }
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("echarts", list1.toString());
    }

    @Test
    public void testMonth() {
        User user = new User();
        user.setId("53");
        user.setCreate_date(new Date());
        userDao.insert(user);
        List<MonthDto> list = userService.queryMonth();
        ArrayList<Integer> list1 = new ArrayList<>();
        for (MonthDto monthDto : list) {
            Integer count = monthDto.getCount();
            list1.add(count);
        }
        System.out.println(list1);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("month", list1.toString());
    }

}
