package com.fhswar.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.entity.Cart;
import com.fhswar.entity.Product;
import com.fhswar.entity.User;
import com.fhswar.mapper.CartMapper;
import com.fhswar.service.CartService;
import com.fhswar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("//cart")
public class CartController {

    // 之前就发现了，mapper 的 insert 和 service 的 save 作用是一样的
//    @Autowired
//    CartMapper cartMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    ProductService productService;

    // 果然要熟悉业务，这里隐含着加够每次只能加一个商品，所以下面操作的都是某具体商品
    // 根据商品价格数量可以算总额，根据 session 可以拿 user 的 id
    // 看看数据库这个表有啥，对应上。直接梭的，算的，根据 session 拿的，都搞搞齐，再梭进数据库，并更新库存
    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(@PathVariable("id") Integer productId,
                      @PathVariable("price") Float price,
                      @PathVariable("quantity") Integer quantity,
                      HttpSession session) {
        Float cost = price * quantity;
        // user 对象是 session 的一个 attribute, 这是 UserController 的 login 里 set 的
        User user = (User) session.getAttribute("user");
        // 这里要 new 一个 cart 是我没想到的，我还是太菜了
        Cart cart = new Cart();
        cart.setCost(cost);
        cart.setProductId(productId);
        cart.setUserId(user.getId());

        // 这里我自己加了一条逻辑，作用是避免在购物车中同一商品占多行
        Cart cartDB = this.cartService.whetherExistInDB(productId, user.getId());
        if (cartDB == null) {
            // 自己写的时候漏了这个
            cart.setQuantity(quantity);
            this.cartService.save(cart); // 购物车里没同款就存进去
//        this.cartMapper.insert(cart); 这个和上面效果一摸一样
        } else {
            cart.setQuantity(cartDB.getQuantity() + quantity); //购物车里有同款的话更新就好了
            QueryWrapper<Cart> wrapper = new QueryWrapper<>();
            wrapper.eq("product_id", productId);
            wrapper.eq("user_id", user.getId());
            this.cartService.update(cart, wrapper);
            // 成功了！牛逼！！！
        }

        // 加购物车后更新库存
        Product product = this.productService.getById(productId); // 查出来才能更新啊傻仔
        product.setStock(product.getStock() - quantity);
        this.productService.updateById(product);
        return "redirect:/cart/cartVO";
    }

    //上面把加购形成的数据存进数据库并更新库存，接下来是封装数据跳转到 settlement1
    // 这里又有新认识，只有对应的 Controller 才会调对应的 Service，session 只在 controller 才拿得到
    @GetMapping("/cartVO")
    public ModelAndView cartVOs(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("carts", // 用的人家模板，名字跟人家保持一致才能用
                this.cartService.findCartVOListByUserId(user.getId()));
        return modelAndView;
    }

    // settlement.js 里面说明了请求类型和应返数据，这里对着来设注解和返回值
    @PostMapping("/updateCart/{type}/{id}/{productId}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(
            @PathVariable("type") String type,
            @PathVariable("id") Integer id,
            @PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity,
            @PathVariable("cost") Float cost
    ){
        boolean result = this.cartService.updateCart(type, id, productId, quantity, cost);
        if(result) return "success";
        return "fail";
    }

    @GetMapping("/removeCart/{id}")
    public synchronized String removeCart(@PathVariable("id") Integer id){
        //修改库存
        Cart cart = this.cartService.getById(id);
        Product product = this.productService.getById(cart.getProductId());
        product.setStock(product.getStock()+cart.getQuantity());
        this.productService.updateById(product);
        //删除购物车
        this.cartService.removeById(id);
        return "redirect:/cart/cartVO";
    }
}

