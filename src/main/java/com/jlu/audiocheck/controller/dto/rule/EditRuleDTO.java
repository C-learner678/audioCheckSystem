package com.jlu.audiocheck.controller.dto.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="编辑规则DTO")
public class EditRuleDTO {
    @NotNull(message = "规则ID不能为空")
    @ApiModelProperty("规则ID")
    private Integer id;
    @NotBlank(message = "规则名称不能为空")
    @ApiModelProperty("规则名称")
    private String name;
    @NotBlank(message = "规则内容不能为空")
    @ApiModelProperty("规则内容")
    private String context;
    @ApiModelProperty("规则描述")
    private String description;
}
