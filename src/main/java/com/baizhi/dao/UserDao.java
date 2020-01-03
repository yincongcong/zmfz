package com.baizhi.dao;

import com.baizhi.entity.AddressDto;
import com.baizhi.entity.DayDto;
import com.baizhi.entity.MonthDto;
import com.baizhi.entity.User;

import java.util.List;

public interface UserDao {
    //通过id删除
    int deleteByPrimaryKey(String id);

    //添加数据
    int insert(User record);

    //有选择的添加
    int insertSelective(User record);

    //通过id查询
    User selectByPrimaryKey(String id);

    //修改用户信息
    int updateByPrimaryKeySelective(User record);

    //通过id修改用户信息
    int updateByPrimaryKey(User record);

    //查询最近7天的用户量
    List<DayDto> queryDay();

    //查询1-12月
    List<MonthDto> queryMonth();

    //用户地理图
    List<AddressDto> queryAddress();

}