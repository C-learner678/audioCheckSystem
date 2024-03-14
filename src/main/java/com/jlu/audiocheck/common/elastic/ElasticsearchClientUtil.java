package com.jlu.audiocheck.common.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchClientUtil {
    private static String host;
    private static Integer port;
    private static String auth;
    private static ElasticsearchClient singleton;
    private ElasticsearchClientUtil(){

    }
    public static ElasticsearchClient getInstance(){
        if(singleton == null){
            synchronized (ElasticsearchClientUtil.class){
                if(singleton == null){
                    RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"));
                    Header[] defaultHeaders = new Header[]{new BasicHeader("Authorization", auth)};
                    builder.setDefaultHeaders(defaultHeaders);
                    RestClient restClient = builder.build();
                    ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
                    singleton = new ElasticsearchClient(transport);
                }
            }
        }
        return singleton;
    }
    @Autowired
    public void setPath(@Value("${elastic.host}") String host, @Value("${elastic.port}") String port, @Value("${elastic.auth}") String auth){
        ElasticsearchClientUtil.host = host;
        ElasticsearchClientUtil.port = Integer.valueOf(port);
        ElasticsearchClientUtil.auth = auth;
    }
}
