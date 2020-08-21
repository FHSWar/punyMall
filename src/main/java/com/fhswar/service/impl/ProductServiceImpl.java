package com.fhswar.service.impl;

import com.fhswar.entity.Product;
import com.fhswar.mapper.ProductMapper;
import com.fhswar.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
