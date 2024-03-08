package com.jlu.audiocheck.controller.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="修改密码DTO")
public class ModifyPasswordDTO {
    @NotNull
    @ApiModelProperty("用户名")
    private String name;
    @NotNull
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @NotNull
    @ApiModelProperty("新密码")
    private String newPassword;
}
