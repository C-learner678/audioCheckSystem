package com.jlu.audiocheck.common.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class FFmpegUtil {
    private static final String ffmpegPath = "C:/Program Files (x86)/ffmpeg/ffmpeg-6.1.1-full_build/bin/ffmpeg.exe";
    private static final String ffprobePath = "C:/Program Files (x86)/ffmpeg/ffmpeg-6.1.1-full_build/bin/ffprobe.exe";

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
}
