package com.jlu.audiocheck.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static Result result(Integer code, String message, Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static Result success() {
        return result(200, "操作成功", null);
    }

    public static Result success(Object data) {
        return result(200, "操作成功", data);
    }

    public static Result fail(String message, Object data){
        return result(400, message, data);
    }

    public static Result fail(String message){
        return result(400, message, null);
    }
}
