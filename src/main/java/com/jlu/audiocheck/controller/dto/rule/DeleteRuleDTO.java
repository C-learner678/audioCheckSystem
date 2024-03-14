package com.jlu.audiocheck.controller.dto.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="删除规则DTO")
public class DeleteRuleDTO {
    @NotNull(message = "规则ID不能为空")
    @ApiModelProperty("规则ID")
    private Integer id;
}
