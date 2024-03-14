package com.jlu.audiocheck.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Text {
    private Integer userId;
    private Integer categoryId;
    private String name;
    private String context;
    private Date uploadTime;
}
