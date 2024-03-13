package com.jlu.audiocheck.controller.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description="用户登录DTO")
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String name;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
}
