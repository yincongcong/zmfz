package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.service.AdminService;
import com.baizhi.service.BannerService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("poi")
public class ExcleExportController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AdminService adminService;

    //带图片导出
    @RequestMapping("poiOut")
    public void excleOut(HttpServletResponse response, HttpSession session) throws Exception {
        //查所有
        List<Banner> banners = bannerService.queryAllBanner();
        //获取图片的绝对路径
        String realPath = session.getServletContext().getRealPath("/img");
        for (Banner banner : banners) {
            /*E:\180班\第三阶段框架阶段\IDEACode\zmfz\src\main\webapp\img\*/
            banner.setImg(realPath + "/" + banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("大图轮播管理", "大图轮播"),
                Banner.class, banners);
        response.setHeader("content-disposition", "attachment;filename=banner.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @RequestMapping("adminPoi")
    public void adminPoi(HttpSession session, HttpServletResponse response) throws Exception {
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
        List<Admin> admins = adminService.queryAll();
        for (int i = 0; i < admins.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(admins.get(i).getId());
            row1.createCell(1).setCellValue(admins.get(i).getUsername());
            row1.createCell(2).setCellValue(admins.get(i).getPassword());
        }
        response.setHeader("content-disposition", "attachment;filename=admin.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        //指定路径
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
