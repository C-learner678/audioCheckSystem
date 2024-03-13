package com.jlu.audiocheck.common.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Slf4j
@Component
public class ScheduledTasks {
    @Value("${file.upload.path}")
    private String path;

    //清理过期文件
    @Scheduled(fixedRate = 3600 * 1000) //3600s
    public void deleteFile() {
        Date date = new Date(System.currentTimeMillis() - 3600 * 1000);
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (new Date(file.lastModified()).before(date)) {
                    file.delete();
                }
            }
        }
        log.info("已清理过期文件");
    }
}
