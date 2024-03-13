package com.jlu.audiocheck.controller.dto.audio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description="识别文件DTO")
public class RecognizeFileDTO {
    @NotBlank
    @ApiModelProperty("文件格式")
    private String format;
    @NotNull
    @ApiModelProperty("采样率")
    private Integer rate;
    @NotEmpty
    @ApiModelProperty("文件列表")
    private List<Map<String, String>> fileList;
}
