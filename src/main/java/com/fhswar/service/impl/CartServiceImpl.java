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
            BeanUtils.copyProperties(specificRow, cartVO);
            BeanUtils.copyProperties(productById, cartVO);
            result.add(cartVO);
        }
        return result;
    }
}
