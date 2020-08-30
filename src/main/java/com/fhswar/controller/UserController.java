package com.fhswar.controller;


import com.fhswar.entity.User;
import com.fhswar.enums.WrongRegisteringEnums;
import com.fhswar.exceptions.WrongRegisteringException;
import com.fhswar.service.CartService;
import com.fhswar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@Controller
@RequestMapping("//user")
public class    UserController {
    @Autowired // 依赖接口不依赖实现！！！桥接(Bridge)模式！！！
    UserService userService;
    @Autowired
    CartService cartService;

    // 注册和登录都有不能明文显示的内容，当然要用 post
    @PostMapping("/register")
    public String register(User user){
        // exception 居然是 new 出来的。
        // 虽然 html 已经做过非空校验，但是黑客可以通过 postman 等工具绕过前端的非空校验，所以 server 要再校验一次。
        if(user == null){
            throw new WrongRegisteringException(WrongRegisteringEnums.USER_NOT_EXIST);
        }
        // 单把性别校验拉出来是为了进行 sex 和 gender 的转换。
        boolean haveGender = this.userService.checkGender(user);
        if(!haveGender) throw new WrongRegisteringException(WrongRegisteringEnums.USER_SAVE_FAIL);
        // 没存进去当然也报错
        boolean haveSaved = this.userService.insertIntoDB(user);
        if(!haveSaved) throw new WrongRegisteringException(WrongRegisteringEnums.USER_SAVE_FAIL);
        // 一切顺利才会进行页面跳转
        return "login";
    }

    // thymeleaf 厉害啊，这儿既能接 user 又能接 session。
    @PostMapping("/login")
    public String login(User user, HttpSession session){
        //这里不成功有两种情况，一是输入为空，这时应弹警告；二是查无此人，这时返回登陆页面。
        if (user == null) throw new WrongRegisteringException(WrongRegisteringEnums.USER_NOT_EXIST);

        User checkUser = this.userService.checkUser(user);
        if (checkUser != null) {session.setAttribute("user", checkUser); return "redirect:/";}
        return "login";
        // 说得严肃一点，这里进行了非空校验和符合性校验。
    }

    @GetMapping("/logOut")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("carts",this.cartService.findCartVOListByUserId(user.getId()));
        return modelAndView;
    }

    // 这个的作用是让测试后台映射以及从数据库到浏览器的数据是否贯通,不删，留作知识点。
    @GetMapping("/list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("list", this.userService.list());
        return modelAndView;
    }
}

