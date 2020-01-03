package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDao {
    //通过姓名查询
    public Admin selectName(String username);

    //查所有
    public List<Admin> queryAll();
}
