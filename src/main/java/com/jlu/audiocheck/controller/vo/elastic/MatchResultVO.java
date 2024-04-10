package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class MatchResultVO implements Serializable {
    private String name;
    private String subPatterns;
    private ArrayList<MatchDataVO> matchData = new ArrayList<>();
}
