package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.io.Serializable;

@Data
public class MatchDataVO  implements Serializable {
    private String subPattern;
    private String text;
}
