package com.fhswar.service;

import com.fhswar.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fhswar.entity.User;

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
}
