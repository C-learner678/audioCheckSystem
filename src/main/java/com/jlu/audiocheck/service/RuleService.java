package com.jlu.audiocheck.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.rule.CreateRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.DeleteRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.EditRuleDTO;
import com.jlu.audiocheck.controller.dto.rule.GetRuleListDTO;
import com.jlu.audiocheck.entity.Rule;
import com.jlu.audiocheck.mapper.RuleMapper;
import com.jlu.audiocheck.ruleTool.RuleToolErrorListener;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesLexer;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    @Autowired
    private RuleMapper ruleMapper;

    public Result getRuleList(GetRuleListDTO getRuleListDTO){
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if(getRuleListDTO.getName() != null && !getRuleListDTO.getName().isEmpty()) {
            queryWrapper.like("name", getRuleListDTO.getName());
        }
        queryWrapper.eq("status", 0);
        queryWrapper.orderByDesc("id");
        Page<Rule> page = new Page<>();
        page.setCurrent(getRuleListDTO.getCurrentPage());
        page.setSize(getRuleListDTO.getPageSize());
        Page<Rule> ruleList = ruleMapper.selectPage(page, queryWrapper);
        return Result.success(ruleList);
    }

    public Result createRule(CreateRuleDTO createRuleDTO){
        //check
        String ruleContext = createRuleDTO.getContext();
        checkRule(ruleContext);
        //insert
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        Rule rule = new Rule();
        rule.setUserId(userId);
        rule.setName(createRuleDTO.getName());
        rule.setContext(createRuleDTO.getContext());
        rule.setDescription(createRuleDTO.getDescription());
        if(ruleMapper.insert(rule) > 0) {
            return Result.success();
        }else{
            return Result.fail("新增匹配规则失败！");
        }
    }

    public Result editRule(EditRuleDTO editRuleDTO){
        //check
        String ruleContext = editRuleDTO.getContext();
        checkRule(ruleContext);
        //edit
        Rule rule = ruleMapper.selectById(editRuleDTO.getId());
        rule.setName(editRuleDTO.getName());
        rule.setContext(ruleContext);
        rule.setDescription(editRuleDTO.getDescription());
        ruleMapper.updateById(rule);
        return Result.success();
    }

    public Result deleteRule(DeleteRuleDTO deleteRuleDTO){
        Rule rule = ruleMapper.selectById(deleteRuleDTO.getId());
        rule.setStatus(1);
        ruleMapper.updateById(rule);
        return Result.success();
    }

    private void checkRule(String context){
        RuleToolErrorListener listener = new RuleToolErrorListener();
        CharStream inputStream = CharStreams.fromString(context);
        parserRulesLexer lexer = new parserRulesLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parserRulesParser parser = new parserRulesParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);
        ParseTree tree = parser.pattern();
    }
}
