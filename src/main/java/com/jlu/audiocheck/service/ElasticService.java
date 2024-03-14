package com.jlu.audiocheck.service;

import cn.dev33.satoken.stp.StpUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.jlu.audiocheck.common.elastic.ElasticsearchClientUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.elastic.AddDocDTO;
import com.jlu.audiocheck.entity.Text;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class ElasticService {
    private final ElasticsearchClient client = ElasticsearchClientUtil.getInstance();

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
}
