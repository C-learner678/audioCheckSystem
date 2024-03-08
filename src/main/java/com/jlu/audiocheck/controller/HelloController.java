package com.jlu.audiocheck.controller;

import com.jlu.audiocheck.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {
    @GetMapping("/hello")
    public Result index() {
        return Result.success("Hello World");
    }
}
