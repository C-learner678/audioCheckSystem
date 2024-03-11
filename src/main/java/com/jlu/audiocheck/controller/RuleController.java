package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.rule.CreateRuleDTO;
import com.jlu.audiocheck.service.RuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping("/getRuleList")
    @ApiOperation(value = "获取匹配规则表")
    public Result getRuleList() {
        StpUtil.checkLogin();
        return ruleService.getRuleList();
    }

    @PostMapping("/createRule")
    @ApiOperation(value = "新增匹配规则")
    public Result createRule(@Valid @RequestBody CreateRuleDTO createRuleDTO) {
        StpUtil.checkLogin();
        return ruleService.createRule(createRuleDTO);
    }
}
