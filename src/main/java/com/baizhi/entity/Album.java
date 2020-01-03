package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    private String id;//id
    private String title;//标题
    private String img;//路径
    private String score;//分数
    private String author;//作者
    private String broadcaster;//播音者
    private String count;//章节数
    private String brief;//内容简介
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_date;//发布时间
    private String status;//状态
    private String other;//预留字段
}
