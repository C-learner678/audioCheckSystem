package com.jlu.audiocheck.elasticSearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesBaseVisitor;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ElasticSearch extends parserRulesBaseVisitor<MatchResult> {
    private ElasticsearchClient client;

    private Query userFilter;
    private Map<String, HighlightField> highlightFieldMap = new HashMap<>();

    private MatchResult totalResult = null; //用于非表达式

    public ElasticSearch(Long userId, String preTag, String postTag, ElasticsearchClient client){
        this.client = client;
        this.userFilter = TermQuery.of(q -> q.field("userId").value(userId))._toQuery();
        this.highlightFieldMap.put("context", new HighlightField.Builder().preTags(preTag).postTags(postTag)
                .fragmentSize(5).numberOfFragments(5).build());
    }

    //匹配全文
    @Override
    public MatchResult visitPattern(parserRulesParser.PatternContext ctx) {
        MatchResult result = visit(ctx.expr());
        return result;
    }

    //匹配或表达式
    @Override
    public MatchResult visitOrExpression(parserRulesParser.OrExpressionContext ctx) {
        MatchResult result = new MatchResult();
        MatchResult leftResult = visit(ctx.leftExpr);
        MatchResult rightResult = visit(ctx.rightExpr);
        //合并
        leftResult.getData().forEach((id, leftList) -> {
            result.getData().put(id, leftList);
        });
        rightResult.getData().forEach((id, rightList) -> {
            if(result.getData().containsKey(id)){
                result.getData().get(id).getPatternToHighlightedText().putAll(rightList.getPatternToHighlightedText());
            }else{
                result.getData().put(id, rightList);
            }
        });
        return result;
    }

    //匹配和表达式
    @Override
    public MatchResult visitAndExpression(parserRulesParser.AndExpressionContext ctx) {
        MatchResult result = new MatchResult();
        MatchResult leftResult = visit(ctx.leftExpr);
        MatchResult rightResult = visit(ctx.rightExpr);
        leftResult.getData().forEach((id, leftList) -> {
            if(rightResult.getData().containsKey(id)){
                result.getData().put(id, leftList);
                result.getData().get(id).getPatternToHighlightedText().putAll(
                        rightResult.getData().get(id).getPatternToHighlightedText());
            }
        });
        return result;
    }

    //匹配括号
    @Override
    public MatchResult visitParenExpression(parserRulesParser.ParenExpressionContext ctx) {
        return visit(ctx.expr());
    }

    //匹配非表达式
    @Override
    public MatchResult visitNotExpression(parserRulesParser.NotExpressionContext ctx) {
        MatchResult result = new MatchResult();
        MatchResult notResult = visit(ctx.expr());
        //获取全部待查询的文件
        if(this.totalResult == null){
            try {
                this.totalResult = new MatchResult();
                SearchResponse<ObjectNode> response = client.search(s -> s.index("texts")
                                .query(q -> q.bool(b -> b.filter(userFilter))),
                        ObjectNode.class
                );
                List<Hit<ObjectNode>> hits = response.hits().hits();
                for (Hit<ObjectNode> hit : hits) {
                    if (hit.source() != null) {
                        String id = hit.id();
                        String name = hit.source().get("name").asText();
                        //无高亮
                        MatchData data = new MatchData(name, new HashMap<>());
                        this.totalResult.getData().put(id, data);
                    }
                }
            }catch (IOException e){
                log.error(e.toString());
                this.totalResult = null;
            }
        }
        //从全部文件中去掉有关键字的部分
        if(this.totalResult != null) {
            this.totalResult.getData().forEach((id, totalList) -> {
                if (!notResult.getData().containsKey(id)) {
                    result.getData().put(id, totalList);
                }
            });
        }
        return result;
    }

    //匹配后表达式
    @Override
    public MatchResult visitAfterExpression(parserRulesParser.AfterExpressionContext ctx) {
        int distance = Integer.parseInt(ctx.INT().getText());
        String leftWord = ctx.WORD(0).getText();
        String rightWord = ctx.WORD(1).getText();
        return matchAfter(leftWord, rightWord, distance);
    }

    //匹配默认的后表达式
    @Override
    public MatchResult visitDefaultAfterExpression(parserRulesParser.DefaultAfterExpressionContext ctx) {
        String leftWord = ctx.WORD(0).getText();
        String rightWord = ctx.WORD(1).getText();
        return matchAfter(leftWord, rightWord, 10);
    }

    //匹配单个词
    @Override
    public MatchResult visitWordExpression(parserRulesParser.WordExpressionContext ctx) {
        String pattern = ctx.WORD().getText();
        Query searchText = MatchPhraseQuery.of(q -> q.field("context").query(pattern).slop(0))._toQuery();
        return getResult(searchText, pattern);
    }

    //后匹配
    //先查询左单词出现的全部位置，再依次寻找有无右单词跟在左单词后且距离在distance以内
    private MatchResult matchAfter(String leftWord, String rightWord, int distance){
        String pattern = leftWord + "#" + rightWord + "#" + distance;
        Query searchText = MatchPhraseQuery.of(q ->
                q.field("context").query(leftWord + rightWord).slop(distance))._toQuery();
        return getResult(searchText, pattern);
    }

    private MatchResult getResult(Query searchText, String pattern){
        MatchResult result = new MatchResult();
        try {
            SearchResponse<ObjectNode> response = client.search(s -> s.index("texts")
                            .query(q -> q.bool(b -> b.must(searchText).filter(userFilter)))
                            .highlight(h -> h.fields(highlightFieldMap)),
                    ObjectNode.class
            );
            List<Hit<ObjectNode>> hits = response.hits().hits();
            for (Hit<ObjectNode> hit: hits) {
                if(hit.source() != null) {
                    String id = hit.id();
                    String name = hit.source().get("name").asText();
                    List<String> highlights = hit.highlight().get("context");
                    HashMap<String, List<String>> map = new HashMap<>();
                    map.put(pattern, highlights);
                    MatchData data = new MatchData(name, map);
                    result.getData().put(id, data);
                }
            }
        }catch (IOException e){
            log.error(e.toString());
        }
        return result;
    }
}
