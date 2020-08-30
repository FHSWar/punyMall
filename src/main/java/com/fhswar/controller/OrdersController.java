package com.fhswar.controller;


import com.fhswar.entity.Orders;
import com.fhswar.entity.User;
import com.fhswar.service.CartService;
import com.fhswar.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("//orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public ModelAndView create(String selectAddress, Float cost, HttpSession session, String address, String remark){
        User user = (User) session.getAttribute("user");
        Orders orders = this.ordersService.create(selectAddress, cost, user,address,remark);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("orders",orders);
        modelAndView.addObject("carts",this.cartService.findCartVOListByUserId(user.getId()));
        return modelAndView;
    }
}

