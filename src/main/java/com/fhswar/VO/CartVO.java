package com.fhswar.VO;

import lombok.Data;

@Data // 这个注解差点儿又忘了
public class CartVO {
    // 数据库 cart 表里没有商品图片，商品名称，商品单价
    Integer id; // 哦哦，删除某购物车记录是要用到这个的
    Integer productId;
    Integer quantity;
    Float cost;
//    Integer userId; 这个前端页面应该也不需要
    String name;
    Float price;
    Integer stock; // 结算页面也用到了库存
    String fileName;
}
