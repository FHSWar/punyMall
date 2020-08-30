package com.fhswar.service;

import com.fhswar.VO.OrdersVO;
import com.fhswar.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fhswar.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
public interface OrdersService extends IService<Orders> {
    public Orders create(String selectAddress, Float cost, User user, String address, String remark);
    public List<OrdersVO> getAllByUserId(Integer userId);
}
