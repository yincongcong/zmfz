package com.baizhi.controller;

import com.baizhi.service.AdminService;
import com.baizhi.util.ValidateImageCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController//所有的都以json串的形式返回
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*
     * 验证码
     * */
    @RequestMapping("code")
    public void code(HttpSession session, HttpServletResponse response) {
        //绘制验证码字符
        String code = ValidateImageCodeUtils.getSecurityCode();
        //将获取的验证码字符存至session中
        session.setAttribute("code", code);
        //将字符绘制成图片
        BufferedImage image = ValidateImageCodeUtils.createImage(code);
        //将图片传至页面
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        登录
        参数1:用户名
        参数2：密码
        参数3：用来存储错误信息
    */
    @RequestMapping("login")
    public String login(String username, String password, String code, HttpSession session) {
        //通过Session获取验证码
        System.out.println("提交=======");
        String code1 = (String) session.getAttribute("code");
        //判断输入的密码和图片上的密码是否一致
        if (code.equals(code1)) {
            String login = adminService.login(username, password);
            return login;
        } else {
            return "验证码错误";
        }
    }
}
