package com.jlu.audiocheck.controller.dto.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="获取规则DTO")
public class GetRuleListDTO {
    @ApiModelProperty("规则名称")
    private String name;
    @NotNull(message = "当前页面不能为空")
    @ApiModelProperty("当前页面")
    private Integer currentPage;
    @NotNull(message = "页面大小不能为空")
    @ApiModelProperty("页面大小")
    private Integer pageSize;
}
