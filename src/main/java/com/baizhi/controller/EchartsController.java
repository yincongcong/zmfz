package com.baizhi.controller;

import com.baizhi.entity.DayDto;
import com.baizhi.entity.MapDto;
import com.baizhi.entity.MonthDto;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@ResponseBody
@RequestMapping("echarts")
public class EchartsController {
    @Autowired
    private UserService userService;

    @RequestMapping("getMaps")
    @ResponseBody
    public List<Map<String, Object>> getMaps() {
        List<Map<String, Object>> list1 = new ArrayList<>();
        ArrayList<MapDto> list = new ArrayList<>();
        list.add(new MapDto("北京", new Random().nextInt(1000)));
        list.add(new MapDto("上海", new Random().nextInt(1000)));
        list.add(new MapDto("山西", new Random().nextInt(1000)));
        list.add(new MapDto("陕西", new Random().nextInt(1000)));
        list.add(new MapDto("山东", new Random().nextInt(1000)));
        list.add(new MapDto("天津", new Random().nextInt(1000)));
        list.add(new MapDto("湖北", new Random().nextInt(1000)));
        for (MapDto mapDto : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", mapDto.getName());
            map.put("value", mapDto.getValue());
            list1.add(map);
        }
        return list1;
    }

    //查询最近7天
    @ResponseBody
    @RequestMapping("getDayUser")
    public List<DayDto> getDayUser() {
        List<DayDto> list = userService.queryDay();
        return list;
    }

    @ResponseBody
    @RequestMapping("getMonthUser")
    public List<MonthDto> getMonthUser() {
        List<MonthDto> list = userService.queryMonth();
        return list;
    }

    @ResponseBody
    @RequestMapping("getAddressUser")
    public List<Map<String, Object>> getAddressUser() {
        List<Map<String, Object>> list = userService.queryAddress();
        return list;
    }
}
