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

    // 字符串有个前缀 redirect:
    // 如果不是直接跳转到静态页面，就需要用到 redirect:
    // 数据改由从数据库查之后，UserController 的 login 和 logOut 也要重定向的。
    // 有了下面这个就不需要写 redirect:productCategory/list 这个这么长了。
    @GetMapping("/")
    public String mainData(){return "redirect:productCategory/list";}
}
