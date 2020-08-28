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
    // 这个是我自己想出来的
    Cart whetherExistInDB(Integer productId, Integer userId);
    // 作用是更新数据库 cart 和 product 表，返信号给 ajax 使 jq 对前端页面进行进一步操作
    boolean updateCart(String type,Integer id,Integer productId,Integer quantity,Float cost);
}
