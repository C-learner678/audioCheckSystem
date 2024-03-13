package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.rule.CreateRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.DeleteRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.EditRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.GetRuleListDTO;
import com.jlu.audiocheck.service.RuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping("/getRuleList")
    @ApiOperation(value = "获取匹配规则表")
    public Result getRuleList(@Valid @RequestBody GetRuleListDTO getRuleListDTO) {
        StpUtil.checkLogin();
        return ruleService.getRuleList(getRuleListDTO);
    }

    @PostMapping("/createRule")
    @ApiOperation(value = "新增匹配规则")
    public Result createRule(@Valid @RequestBody CreateRuleDTO createRuleDTO) {
        StpUtil.checkLogin();
        return ruleService.createRule(createRuleDTO);
    }

    @PostMapping("/editRule")
    @ApiOperation(value = "修改匹配规则")
    public Result editRule(@Valid @RequestBody EditRuleDTO editRuleDTO) {
        StpUtil.checkLogin();
        return ruleService.editRule(editRuleDTO);
    }

    @PostMapping("/deleteRule")
    @ApiOperation(value = "删除匹配规则")
    public Result deleteRule(@Valid @RequestBody DeleteRuleDTO deleteRuleDTO) {
        StpUtil.checkLogin();
        return ruleService.deleteRule(deleteRuleDTO);
    }
}
