package com.fhswar.VO;

import com.fhswar.entity.Product;
import lombok.Data;

import java.util.List;

@Data // 傻逼东西，注解都不加，卡了两个钟。不加不光 get set 不会生效，copyProperties 也不会生效。
public class ProductCategoryVO { // 只在数据库和实体类需要的数据不完全匹配时才有封装 VO 的必要
    String name;
    Integer id;
    List<ProductCategoryVO> children;

    // 这个项目的图片不是从数据库里来的，而是存在 static 里面，在下面的属性翻车车。
    String top;
    String banner; // banner 时横幅啊，为什么侧边栏图片要叫 banner，不舒服。
    List<Product> levelOneProduct; // 我总改老师讲课时用的变量名为自己认为语义更通顺的名字，这为排bug增加了难度。
}
