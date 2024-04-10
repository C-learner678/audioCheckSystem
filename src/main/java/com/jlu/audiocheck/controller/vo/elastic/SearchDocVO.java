package com.jlu.audiocheck.controller.vo.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchDocVO implements Serializable {
    private List<TextVO> list = new ArrayList<>();
}
