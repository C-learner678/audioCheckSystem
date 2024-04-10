package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class MatchVO implements Serializable {
    ArrayList<MatchResultVO> list = new ArrayList<>();
}
