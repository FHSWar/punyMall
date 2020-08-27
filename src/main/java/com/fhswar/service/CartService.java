package com.fhswar.service;

import com.fhswar.VO.CartVO;
import com.fhswar.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
public interface CartService extends IService<Cart> {
    // 形参不是 HttpSession 而是 Integer，里面是 userId
    List<CartVO> findCartVOListByUserId(Integer userId);
}
