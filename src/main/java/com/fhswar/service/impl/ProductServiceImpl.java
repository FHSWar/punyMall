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

    // 注意：一二三层都不是具体商品，而是商品集合。每个具体商品都从属于一个一级和一个二级和一个三级
    // 具体商品(product)的 id 和标明层级(category)的 id 那不是一回事儿好吧
    @Override
    public List<Product> findProductByCategory(Integer type, Integer certainLevelCategoryId) {
        // 这里是抄的，想不出来的原因是没想到同一个商品在不同层级的目录对应不同的 category_id
        // 哦哦，低级别的商品从属于高级别的商品
        String column = "";
        switch (type){
            case 1: // 这里有冒号
                column = "categoryLevelone_id";
                break; // java 是要加 break 的。
            case 2:
                column = "categoryLeveltwo_id";
                break;
            case 3:
                column = "categoryLevelthree_id";
                break;
        }

        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq(column, certainLevelCategoryId); // 这里数据库用 one 的 o 没大写就很过分
        return this.productMapper.selectList(wrapper);
    }
}
