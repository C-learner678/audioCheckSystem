package com.jlu.audiocheck.controller.dto.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description="新增规则DTO")
public class CreateRuleDTO {
    @NotBlank(message = "规则名称不能为空")
    @ApiModelProperty("规则名称")
    private String name;
    @NotBlank(message = "规则内容不能为空")
    @ApiModelProperty("规则内容")
    private String context;
    @ApiModelProperty("规则描述")
    private String description;
}
