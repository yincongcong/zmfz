package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String login(String username, String password) {
        Admin admin = adminDao.selectName(username);
        //判断用户名是否存在
        if (admin == null) {
            return "用户名不存在";
        }
        //判断输入的密码是否正确
        if (!password.equals(admin.getPassword())) {
            return "用户名或密码错误";
        }
        return "ok";
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Admin> queryAll() {
        List<Admin> list = adminDao.queryAll();
        return list;
    }
}
