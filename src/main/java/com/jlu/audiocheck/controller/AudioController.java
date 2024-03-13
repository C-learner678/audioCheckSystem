package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.audio.RecognizeFileDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping
public class AudioController {
    @Value("${file.upload.path}")
    private String path;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传音频文件")
    public Result uploadFile(@RequestPart MultipartFile file) throws IOException {
        StpUtil.checkLogin();
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

    @PostMapping("/recognizeFile")
    @ApiOperation(value = "识别音频文件")
    public Result recognizeFile(@Valid @RequestBody RecognizeFileDTO recognizeFileDTO){
        StpUtil.checkLogin();
        if(recognizeFileDTO.getFormat().equals("pcm")) {
            if (recognizeFileDTO.getRate() != 16000 && recognizeFileDTO.getRate() != 8000){
                return Result.fail("采样率错误");
            }
        }
        for (Map<String, String> m : recognizeFileDTO.getFileList()) {
            if (!Files.exists(Paths.get(path + m.get("serverFileName")))) {
                return Result.fail("文件不存在，请重新上传");
            }
        }
        //pcm格式：直接上传给百度
        //其他格式：先用ffmpeg转换到pcm格式，再上传给百度
        return Result.success();
    }
}
