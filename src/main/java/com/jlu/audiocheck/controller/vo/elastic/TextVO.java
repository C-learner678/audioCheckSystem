package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TextVO implements Serializable {
    private String id;
    private Integer categoryId;
    private String name;
    private String context;
    private String uploadTime;
}
