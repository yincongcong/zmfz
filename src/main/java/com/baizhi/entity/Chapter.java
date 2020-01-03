package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
    private String id;//章节id
    private String title;//章节标题
    private String album_id;//专辑id
    private String size;//章节大小
    private String duration;//播放时长
    private String src;//音频路径
    private String status;//音频的状态
    private String other;//预留字段
}
