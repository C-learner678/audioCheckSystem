package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchDocVO {
    private List<TextVO> list = new ArrayList<>();
}
