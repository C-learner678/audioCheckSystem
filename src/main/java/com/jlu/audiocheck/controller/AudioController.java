package com.jlu.audiocheck.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.controller.dto.audio.RecognizeFileDTO;
import com.jlu.audiocheck.service.AudioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping
public class AudioController {
    @Autowired
    private AudioService audioService;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传音频文件")
    public Result uploadFile(@RequestPart MultipartFile file) throws IOException {
        StpUtil.checkLogin();
        return audioService.uploadFile(file);
    }

    @PostMapping("/recognizeFile")
    @ApiOperation(value = "识别音频文件")
    public Result recognizeFile(@Valid @RequestBody RecognizeFileDTO recognizeFileDTO) throws IOException {
        StpUtil.checkLogin();
        return audioService.recognizeFile(recognizeFileDTO);
    }
}
