package com.fhswar.controller;


import com.fhswar.VO.CartVO;
import com.fhswar.entity.Product;
import com.fhswar.entity.User;
import com.fhswar.service.CartService;
import com.fhswar.service.ProductCategoryService;
import com.fhswar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("//product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    CartService cartService;

    // 三个注意点
    // 1. @PathVariable 后面要跟括号 ("xxx")
    // 2. 查询的逻辑已经写在 ServiceImpl 里了，Controller 只需要要做验证和封装。
    // 3. 每个页面查的 modelAndView 都只给自己的页面用，所以这里要再查一次层级数据。又由于 common 直解析 VO，所以这里只能查 VO。
    @GetMapping("/list/{type}/{productId}")
    public ModelAndView list(@PathVariable("type") Integer type,
                             @PathVariable("productId") Integer productId,
                             HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList", this.productService.findProductByCategory(type, productId));
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

    // 只查一个而且数据简单的话，完全用不着封装 VO 嗷     /product/findById/'+${product.id}
    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id,
                                 HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product", this.productService.getById(id));
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

