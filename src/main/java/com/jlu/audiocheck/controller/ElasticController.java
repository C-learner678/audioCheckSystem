package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.elastic.AddDocDTO;
import com.jlu.audiocheck.service.ElasticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping
public class ElasticController {
    @Autowired
    private ElasticService elasticService;

    @PostMapping("/addDoc")
    @ApiOperation(value = "新增文档")
    public Result addDoc(@Valid @RequestBody AddDocDTO addDocDTO) throws IOException {
        StpUtil.checkLogin();
        if(addDocDTO.getCategoryId() == null){
            addDocDTO.setCategoryId(0); //默认类别
        }
        return elasticService.addDoc(addDocDTO);
    }
}
