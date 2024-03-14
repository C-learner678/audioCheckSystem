package com.jlu.audiocheck.common.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FFmpegUtil {
    private static String ffmpegPath;
    private static String ffprobePath;
    private static FFmpegExecutor singleton;
    private FFmpegUtil(){

    }
    public static FFmpegExecutor getInstance() throws IOException {
        if(singleton == null){
            synchronized (FFmpegUtil.class){
                if(singleton == null){
                    singleton = new FFmpegExecutor(new FFmpeg(ffmpegPath), new FFprobe(ffprobePath));
                }
            }
        }
        return singleton;
    }
    @Autowired
    public void setPath(@Value("${ffmpeg.ffmpeg-path}") String path1, @Value("${ffmpeg.ffprobe-path}") String path2){
        ffmpegPath = path1;
        ffprobePath = path2;
    }
}
