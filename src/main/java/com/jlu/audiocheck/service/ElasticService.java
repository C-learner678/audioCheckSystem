package com.jlu.audiocheck.service;

import cn.dev33.satoken.stp.StpUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jlu.audiocheck.common.elastic.ElasticsearchClientUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.elastic.AddDocDTO;
import com.jlu.audiocheck.controller.dto.elastic.DeleteDocDTO;
import com.jlu.audiocheck.controller.dto.elastic.MatchDTO;
import com.jlu.audiocheck.controller.vo.elastic.*;
import com.jlu.audiocheck.elasticSearch.ElasticSearch;
import com.jlu.audiocheck.elasticSearch.MatchResult;
import com.jlu.audiocheck.entity.Text;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesLexer;
import com.jlu.audiocheck.ruleToolGenerated.parserRulesParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ElasticService {
    private final ElasticsearchClient client = ElasticsearchClientUtil.getInstance();

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Result addDoc(AddDocDTO addDocDTO) throws IOException {
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        Integer categoryId = addDocDTO.getCategoryId();
        ArrayList<Text> list = new ArrayList<>();
        for(Map<String, String> m: addDocDTO.getFiles()){
            Text text = new Text();
            text.setUserId(userId);
            text.setCategoryId(categoryId);
            text.setName(m.get("fileName"));
            text.setContext(m.get("context"));
            text.setUploadTime(new Date());
            list.add(text);
        }
        BulkRequest.Builder br = new BulkRequest.Builder();
        for(Text text: list){
            br.operations(op -> op.index(idx -> idx.index("texts").document(text)));
        }
        BulkResponse result = client.bulk(br.build());
        if(result.errors()){
            return Result.fail("添加失败！");
        }
        return Result.success();
    }

    public Result searchDoc() throws IOException {
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        Query userFilter = TermQuery.of(q -> q.field("userId").value(userId))._toQuery();
        SearchResponse<ObjectNode> response = client.search(s -> s.index("texts")
                .query(q -> q.bool(b -> b.filter(userFilter))),
                ObjectNode.class);
        SearchDocVO vo = new SearchDocVO();
        List<Hit<ObjectNode>> hits = response.hits().hits();
        for (Hit<ObjectNode> hit: hits) {
            if(hit == null || hit.source() == null){
                continue;
            }
            TextVO t = new TextVO();
            t.setId(hit.id());
            t.setCategoryId(hit.source().get("categoryId").asInt());
            t.setName((hit.source().get("name")).asText());
            t.setContext((hit.source().get("context")).asText());
            Date date = new Date(hit.source().get("uploadTime").asLong());
            t.setUploadTime(df.format(date));
            vo.getList().add(t);
        }
        return Result.success(vo);
    }

    public Result deleteDoc(DeleteDocDTO deleteDocDTO) throws IOException{
        DeleteResponse response = client.delete(i -> i.index("texts").id(deleteDocDTO.getId()));
        client.indices().refresh(); //刷新
        if(response.result().jsonValue().equals("deleted")) {
            return Result.success();
        }else{
            return Result.fail("删除失败");
        }
    }

    public Result match(MatchDTO matchDTO){
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        String pattern = matchDTO.getPattern();
        CharStream inputStream = CharStreams.fromString(pattern);
        parserRulesLexer lexer = new parserRulesLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parserRulesParser parser = new parserRulesParser(tokens);
        ParseTree tree = parser.pattern();
        ElasticSearch visitor = new ElasticSearch((long) userId, "<span style=\"color: red;\">", "</span>", client);
        MatchResult result = visitor.visit(tree);
        MatchVO matchVO = new MatchVO();
        result.getData().forEach((id, data) -> {
            MatchResultVO matchResultVO = new MatchResultVO();
            matchResultVO.setName(data.getName());
            StringBuilder sb = new StringBuilder();
            data.getPatternToHighlightedText().forEach((subpattern, text) -> {
                MatchDataVO matchDataVO = new MatchDataVO();
                matchDataVO.setSubPattern(subpattern);
                matchDataVO.setText(text);
                if(!sb.toString().isEmpty()){
                    sb.append(",");
                }
                sb.append(subpattern);
                matchResultVO.getMatchData().add(matchDataVO);
            });
            matchResultVO.setSubPatterns(sb.toString());
            matchVO.getList().add(matchResultVO);
        });
        return Result.success(matchVO);
    }
}
