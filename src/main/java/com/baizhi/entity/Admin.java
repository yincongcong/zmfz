package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    private String id;//用户id
    private String username;//用户名
    private String password;//用户密码
    private String other;//预留字段

}
