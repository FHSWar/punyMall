package com.fhswar.controller;


import com.fhswar.VO.CartVO;
import com.fhswar.VO.ProductCategoryVO;
import com.fhswar.entity.User;
import com.fhswar.service.CartService;
import com.fhswar.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@RestController
@RequestMapping("//productCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("categoryVO",this.productCategoryService.getAllProductCategoryVO());

        // 这一段儿在好几个控制器里都得加，目的是登陆后每个页面的右上角购物车内都显示正确的购物车信息
        User user = (User) session.getAttribute("user");
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findCartVOListByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);

        return modelAndView;
    }
}

