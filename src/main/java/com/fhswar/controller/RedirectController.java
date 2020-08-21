package com.fhswar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    // 这个是为了让 <th:block th:include="common.html :: source"></th:block> 被解析，也就是过一遍视图解析器
    // 不加的话 css，js 就不生效
    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }
}
