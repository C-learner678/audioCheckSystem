package com.jlu.audiocheck;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AudioCheckApplicationTests {
    @Test
    void contextLoads() {
        String content = HttpUtil.get("https://blog.csdn.net/vxzhg/article/details/96007335");
        System.out.println(content);
    }
}
