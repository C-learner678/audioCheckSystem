package com.jlu.audiocheck.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.jlu.audiocheck.common.ffmpeg.FFmpegUtil;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import com.jlu.audiocheck.common.okhttp.OkHttpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.audio.RecognizeFileDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
public class AudioService {
    @Value("${file.upload.path}")
    private String path;
    @Value("${baidu.api-key}")
    private String API_KEY;
    @Value("${baidu.secret-key}")
    private String SECRET_KEY;
    @Value("${baidu.cuid}")
    private String cuid;

    @Resource(name = "MyThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    private final OkHttpClient HTTP_CLIENT = OkHttpUtil.getInstance();

    public Result uploadFile(MultipartFile file) throws IOException {
        Assert.notNull(file, "文件不能为空");
        Assert.notNull(file.getOriginalFilename(), "文件不能为空");
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String serverFileName = IdUtil.randomUUID() + extension;
        String serverFilePath = path + serverFileName;
        File dest = new File(serverFilePath);
        Files.copy(file.getInputStream(), dest.toPath());
        return Result.success(serverFileName);
    }

    //pcm格式：直接上传给百度
    //其他格式：先用ffmpeg转换到pcm格式，再上传给百度
    //使用异步方法实现
    public Result recognizeFile(RecognizeFileDTO recognizeFileDTO) throws ExecutionException, InterruptedException {
        if (recognizeFileDTO.getFormat().equals("pcm")) {
            if (!recognizeFileDTO.getRate().equals("16000") && !recognizeFileDTO.getRate().equals("8000")) {
                return Result.fail("采样率错误");
            }
        }
        log.info("当前线程" + Thread.currentThread().getName());
        Future<Result> f = executor.submit(() -> operateFiles(recognizeFileDTO)); //Callable
        return Result.success(f.get());
    }

    @Async("MyThreadPoolTaskExecutor")
    protected Result operateFiles(RecognizeFileDTO recognizeFileDTO) throws IOException {
        log.info("当前线程" + Thread.currentThread().getName());
        ArrayList<ArrayList<Object>> files = new ArrayList<>();
        ArrayList<String> fileNames = new ArrayList<>();
        if (recognizeFileDTO.getFormat().equals("pcm")) {
            for (Map<String, String> m : recognizeFileDTO.getFileList()) {
                String path1 = path + m.get("serverFileName");
                if (!Files.exists(Paths.get(path1))) {
                    return Result.fail("文件" + m.get("fileName") + "不存在，请重新上传");
                } else {
                    String clientName = m.get("fileName");
                    files.add(getFileContentAsBase64(path1, false));
                    fileNames.add(clientName);
                }
            }
        } else {
            for (Map<String, String> m : recognizeFileDTO.getFileList()) {
                String path1 = path + m.get("serverFileName");
                if (!Files.exists(Paths.get(path1))) {
                    return Result.fail("文件" + m.get("fileName") + "不存在，请重新上传");
                }else{
                    int index = m.get("serverFileName").lastIndexOf('.');
                    String newServerName = m.get("serverFileName").substring(0, index) + ".pcm";
                    String path2 = path + newServerName;
                    String clientName = m.get("fileName");
                    FFmpegBuilder builder = new FFmpegBuilder()
                            .setInput(path1)
                            .overrideOutputFiles(true)
                            .addOutput(path2)
                            .setFormat("s16le")
                            .setAudioCodec("pcm_s16le")
                            .setAudioSampleRate(16000)
                            .setAudioChannels(1)
                            .done();
                    FFmpegUtil.getInstance().createJob(builder).run();
                    files.add(getFileContentAsBase64(path2, false));
                    fileNames.add(clientName);
                }
            }
        }
        String format = "pcm";
        String rate = "16000";
        if(recognizeFileDTO.getFormat().equals("pcm")) {
            rate = recognizeFileDTO.getRate();  // 8000/16000
        }
        String channel = "1";
        String token = getAccessToken();
        MediaType mediaType = MediaType.parse("application/json");
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            String speech = (String) files.get(i).get(0);
            int length = (int) files.get(i).get(1);
            RequestBody body = RequestBody.create(mediaType,
                    "{\"format\":\"" + format + "\"," +
                            "\"rate\":\"" + rate + "\"," +
                            "\"channel\":\"" + channel + "\"," +
                            "\"cuid\":\"" + cuid + "\"," +
                            "\"token\":\"" + token + "\"," +
                            "\"speech\":\"" + speech + "\"," +
                            "\"len\":" + length + "}");
            Request request = new Request.Builder()
                    .url("https://vop.baidu.com/server_api")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            String res = (String) jsonObject.getJSONArray("result").get(0);
            HashMap<String, String> map = new HashMap<>();
            map.put("fileName", fileNames.get(i));
            map.put("context", res);
            result.add(map);
        }
        return Result.success(result);
    }

    private ArrayList<Object> getFileContentAsBase64(String path, boolean urlEncode) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, "utf-8");
        }
        ArrayList<Object> result = new ArrayList<>();
        result.add(base64);
        result.add(b.length);
        return result;
    }

    private String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return JSON.parseObject(response.body().string()).getString("access_token");
    }
}
