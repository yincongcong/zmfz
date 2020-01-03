package com.baizhi.service;

import com.baizhi.entity.DayDto;
import com.baizhi.entity.MonthDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询最近7天用户注册量
    public List<DayDto> queryDay();

    //查询1-12月用户注册量
    public List<MonthDto> queryMonth();

    public List<Map<String, Object>> queryAddress();
}
