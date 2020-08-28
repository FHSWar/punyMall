package com.fhswar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.VO.CartVO;
import com.fhswar.entity.Cart;
import com.fhswar.entity.Product;
import com.fhswar.entity.User;
import com.fhswar.mapper.CartMapper;
import com.fhswar.mapper.ProductMapper;
import com.fhswar.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired //老师习惯在 controller 里用 service.save，在 service 里用 mapper.insert，学他好了，虽然作用一样。
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public List<CartVO> findCartVOListByUserId(Integer userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Cart> productList = this.cartMapper.selectList(wrapper);
        // for 出来，梭进去
        List<CartVO> result = new ArrayList<>();
        CartVO cartVO;
        for (Cart specificRow: productList){
            cartVO = new CartVO();
            Product productById = this.productMapper.selectById(specificRow.getProductId());
            BeanUtils.copyProperties(productById, cartVO);
            // 妈的，cart 的复制得放在 product 的后面 cartVO 才能得到 cart 的 id，我他妈爆炸
            BeanUtils.copyProperties(specificRow, cartVO);
            result.add(cartVO);
        }
        return result;
    }

    @Override
    public Cart whetherExistInDB(Integer productId, Integer userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        wrapper.eq("user_id", userId);
        return this.cartMapper.selectOne(wrapper);
    }

    @Override
    public boolean updateCart(String type, Integer id, Integer productId, Integer quantity, Float cost) {
        //修改购物车的数量
        Cart cart = this.cartMapper.selectById(id);
        Product product = this.productMapper.selectById(productId);
        Integer val = null;
        //修改商品库存
        switch (type){
            case "sub":
                val = cart.getQuantity() - quantity;
                product.setStock(product.getStock()+val);
                break;
            case "add":
                val = quantity - cart.getQuantity();
                product.setStock(product.getStock()-val);
                break;
        }
        cart.setQuantity(quantity);
        cart.setCost(cost);
        // 这两行对数据库进行更新，返 1 说明更新成功，这里有隐患，比如数据库一个成功一个不成功。
        int cartRow = this.cartMapper.updateById(cart);
        int productRow = this.productMapper.updateById(product);
        if(cartRow == 1 && productRow == 1) return true;
        return false;
    }
}
