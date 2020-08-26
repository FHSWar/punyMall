package com.fhswar.service;

import com.fhswar.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
public interface ProductService extends IService<Product> {
    // 查出数据库中 product_category 表 type 为 1 的就可以啦，这是梭进 main 页面用的
        List<Product> findProductByCategory(Integer type, Integer certainLevelCategoryId); // 这个入参是我自己写时没想到的
}
