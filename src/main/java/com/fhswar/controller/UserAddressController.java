package com.fhswar.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.entity.User;
import com.fhswar.service.CartService;
import com.fhswar.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("//userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/info")
    public ModelAndView info(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        modelAndView.addObject("userAddressList",this.userAddressService.list(wrapper));
        modelAndView.addObject("carts",this.cartService.findCartVOListByUserId(user.getId()));
        return modelAndView;
    }

}

