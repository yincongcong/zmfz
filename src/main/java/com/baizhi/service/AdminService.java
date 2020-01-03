package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminService {
    //登录
    public String login(String username, String password);

    //查所有
    public List<Admin> queryAll();
}
