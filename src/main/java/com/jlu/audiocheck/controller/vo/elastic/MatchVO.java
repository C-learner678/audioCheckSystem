package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchVO {
    ArrayList<MatchResultVO> list = new ArrayList<>();
}
