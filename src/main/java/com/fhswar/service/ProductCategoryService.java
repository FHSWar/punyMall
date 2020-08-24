package com.fhswar.service;

import com.fhswar.VO.ProductCategoryVO;
import com.fhswar.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
public interface ProductCategoryService extends IService<ProductCategory> {
        List<ProductCategoryVO> getAllProductCategoryVO(); //拿到的是VO
        List<ProductCategory> getProductCategoryById(Integer type, Integer parentId);
}
