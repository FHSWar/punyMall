package com.fhswar.filter;

import com.fhswar.controller.UserController;
import com.fhswar.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 这里的 Filter 用的是 javax.servlet 的 filter，实现 doFilter 方法。
public class UserFilter implements Filter {

    // FilterConfiguration 指定了这个过滤器对哪些请求进行过滤
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 要转成 HttpServletRequest 才能拿到 session
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 没登录加不了购物车，直接转到登录页面
        if(user == null){
            // 这方法第一次用
            response.sendRedirect("/login");
        }else{
            // 这个操作意思是说: 处于登陆状态，放行
            filterChain.doFilter(servletRequest, response);
        }
    }
}
