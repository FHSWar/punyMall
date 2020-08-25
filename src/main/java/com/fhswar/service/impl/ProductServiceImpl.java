package com.fhswar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.entity.Product;
import com.fhswar.mapper.ProductMapper;
import com.fhswar.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> findProductByCategory(Integer levelOneId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("categoryLevelone_id", levelOneId); // 这里数据库用 one 的 o 没大写就很过分
        return this.productMapper.selectList(wrapper);
    }
}
