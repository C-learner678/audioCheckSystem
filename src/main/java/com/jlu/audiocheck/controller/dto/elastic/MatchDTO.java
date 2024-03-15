package com.jlu.audiocheck.controller.dto.elastic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description="质检DTO")
public class MatchDTO {
    @NotBlank(message = "质检规则不能为空")
    @ApiModelProperty("质检规则")
    private String pattern;
}
