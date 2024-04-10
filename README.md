# audioCheckSystem
音频质检系统（Spring Boot+Vue）<br>

使用文档：<br>
readme.docx<br>

开发工具：<br>
1.Spring Boot<br>
2.MyBatis-Plus<br>
3.Sa-Token<br>
4.Vue<br>
5.Element<br>
6.FFmpeg<br>
7.百度语音识别服务接口<br>
8.ANTLR4<br>
9.Elasticsearch<br>
10.Redis

建立Elastic数据库：<br>
PUT /texts
```
{
  "mappings": {
    "properties": {
      "userId":{
        "type": "long"
      },
      "categoryId":{
        "type": "long"
      },
      "name":{
        "type": "keyword"
      },
      "context":{
        "type": "text",
        "analyzer": "standard"
      },
      "uploadTime":{
        "type": "date"
      }
    }
  }
}
```


