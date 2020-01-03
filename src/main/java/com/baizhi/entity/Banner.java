package com.baizhi.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//支持链式调用
public class Banner implements Serializable {
    @Excel(name = "id", orderNum = "1")
    private String id;
    @Excel(name = "title", orderNum = "1")
    private String title;
    @Excel(name = "图片", type = 2, width = 40, height = 20, imageType = 1)
    private String img;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传日期", format = "yyyy-MM-dd", isImportField = "true_st", width = 20)
    private Date create_date;
    @Excel(name = "status", orderNum = "1")
    private String status;
    private String other;
}
