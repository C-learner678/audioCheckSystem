package com.jlu.audiocheck.controller.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="用户")
public class UserDTO {
    @NotNull
    @ApiModelProperty("用户名")
    private String name;
    @NotNull
    @ApiModelProperty("密码")
    private String password;
}
