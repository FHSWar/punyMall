package com.fhswar.service.impl;

import com.fhswar.entity.Orders;
import com.fhswar.mapper.OrdersMapper;
import com.fhswar.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
