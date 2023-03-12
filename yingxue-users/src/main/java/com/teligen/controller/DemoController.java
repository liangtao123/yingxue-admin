package com.teligen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangtao
 * @date 2023-03-12 19:39
 * @desc: 配置网关 进行测试的Controller
 */

@RestController
@RequestMapping("/demos")
@Slf4j
public class DemoController {
    @GetMapping
    public String demos(){
        log.info("hello,users demos");
        return "hello,users demos";
    }
}
