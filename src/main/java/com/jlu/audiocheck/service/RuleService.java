package com.jlu.audiocheck.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.rule.CreateRuleDTO;
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

import java.util.List;

@Service
public class RuleService {
    @Autowired
    private RuleMapper ruleMapper;

    public Result getRuleList(){
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Rule> ruleList = ruleMapper.selectList(queryWrapper);
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
