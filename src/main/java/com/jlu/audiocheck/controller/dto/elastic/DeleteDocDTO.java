package com.jlu.audiocheck.controller.dto.elastic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="删除文件DTO")
public class DeleteDocDTO {
    @NotNull(message = "文件ID不能为空")
    @ApiModelProperty("文件ID")
    private String id;
}
