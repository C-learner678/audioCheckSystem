package com.jlu.audiocheck.controller.dto.elastic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description="识别文件DTO")
public class AddDocDTO {
    @ApiModelProperty("文件类别")
    private Integer categoryId;
    @NotEmpty(message = "文件列表不能为空")
    @ApiModelProperty("文件列表不能为空")
    private List<Map<String, String>> files;
}
