package com.fhswar.VO;

import lombok.Data;

import java.util.List;

@Data // 傻逼东西，注解都不加，卡了两个钟。不加不光 get set 不会生效，copyProperties 也不会生效。
public class ProductCategoryVO {
    String name;
    Integer id;
    List<ProductCategoryVO> children;
}
