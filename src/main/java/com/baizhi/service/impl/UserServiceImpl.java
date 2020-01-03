package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.AddressDto;
import com.baizhi.entity.DayDto;
import com.baizhi.entity.MonthDto;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public List<DayDto> queryDay() {
        //调用方法
        List<DayDto> user = userDao.queryDay();

        return user;
    }

    /* public List<MonthDto> query(){
         List<DayDto> user = userDao.queryDay();
         for (int i = 1;i<=12;i++){

         }
     }*/
    @Override
    public List<MonthDto> queryMonth() {
        //调用方法
        List<MonthDto> monthDtos = userDao.queryMonth();

        return monthDtos;
    }


    @Override
    public List<Map<String, Object>> queryAddress() {
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<AddressDto> list = userDao.queryAddress();
        for (AddressDto addressDto : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", addressDto.getAddress());
            map.put("value", addressDto.getCounts());
            list1.add(map);

        }
        return list1;
    }
}
