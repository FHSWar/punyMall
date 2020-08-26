package com.fhswar.controller;


import com.fhswar.entity.Product;
import com.fhswar.service.ProductCategoryService;
import com.fhswar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


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

    // 三个注意点
    // 1. @PathVariable 后面要跟括号 ("xxx")
    // 2. 查询的逻辑已经写在 ServiceImpl 里了，Controller 只需要要做验证和封装。
    // 3. 每个页面查的 modelAndView 都只给自己的页面用，所以这里要再查一次层级数据。又由于 common 直解析 VO，所以这里只能查 VO。
    @GetMapping("/list/{type}/{productId}")
    public ModelAndView list(@PathVariable("type") Integer type, @PathVariable("productId") Integer productId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList", this.productService.findProductByCategory(type, productId));
        modelAndView.addObject("categoryVO",this.productCategoryService.getAllProductCategoryVO());
        return modelAndView;
    }

    // 只查一个而且数据简单的话，完全用不着封装 VO 嗷     /product/findById/'+${product.id}
    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product", this.productService.getById(id));
        modelAndView.addObject("categoryVO",this.productCategoryService.getAllProductCategoryVO());
        return modelAndView;
    }
}

