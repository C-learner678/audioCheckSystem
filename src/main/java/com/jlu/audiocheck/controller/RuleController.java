package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RuleController {
    @PostMapping("/getRuleList")
    @ApiOperation(value = "获取匹配规则表")
    public Result getRuleList() {
        StpUtil.checkLogin();
        return Result.success();
    }
}
