package com.fhswar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.VO.ProductCategoryVO;
import com.fhswar.entity.ProductCategory;
import com.fhswar.mapper.ProductCategoryMapper;
import com.fhswar.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
        implements ProductCategoryService {

    @Autowired // 用 mapper 的时候得加上前缀 this
    ProductCategoryMapper productCategoryMapper;

    // 发现个怪浪的现象，用 test 跑这个方法会跑一个数组边界溢出的异常但不影响运行。但启动 Spring Boot 之后，可变数组溢出啥吖
    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        List<ProductCategoryVO> levelOneVOList = new ArrayList<>();
        ProductCategoryVO levelOneVO; // 这是在 for 外部做个坑位给复制用，减少栈内存消耗

        List<ProductCategory> categoryLevelTwo; // 不能把 new 提到 for 循环外面，否则上级级 VO 会包含错误的下级 VO
        ProductCategoryVO levelTwoVO; // 这个在 for 循环外面 new 也不行，得出来的二三级 VO 是错误的。

        List<ProductCategory> categoryLevelThree;
        ProductCategoryVO levelThreeVO;

        List<ProductCategoryVO> levelThreeVOList;
        List<ProductCategoryVO> levelTwoVOList;


        List<ProductCategory> categoryLevelOne = this.getProductCategoryById(1, 0);
        for (ProductCategory levelOneIndividual: categoryLevelOne){
            levelOneVO = new ProductCategoryVO();
            BeanUtils.copyProperties(levelOneIndividual, levelOneVO); // 作用是复制第一个参数中属性给第二个参数对得上号的属性。
            categoryLevelTwo = // 能把 getId 写成 getType 我也真的傻出水平。
                    this.getProductCategoryById(2, levelOneIndividual.getId());
            levelTwoVOList = new ArrayList<>();
            for (ProductCategory levelTwoIndividual: categoryLevelTwo){
                levelTwoVO = new ProductCategoryVO();
                BeanUtils.copyProperties(levelTwoIndividual, levelTwoVO);
                categoryLevelThree =
                        this.getProductCategoryById(3, levelTwoIndividual.getId());
                levelThreeVOList = new ArrayList<>();
                for (ProductCategory levelThreeIndividual: categoryLevelThree){
                    levelThreeVO = new ProductCategoryVO();
                    BeanUtils.copyProperties(levelThreeIndividual, levelThreeVO);
                    levelThreeVOList.add(levelThreeVO);
                }
                // 这里一开始也没加导致 VO children 为 null。害，数据库哪儿来的children，ong仔，当然是我们自己封装的啦。
                levelTwoVO.setChildren(levelThreeVOList);
/*                levelThreeVOList = null; 如果在 for 循环外 List<ProductCategoryVO> levelThreeVOList = new ArrayList<>()
                                        这个 null 赋值操作会导致空指针异常，如果把 new 放在 for 循环内部，这个赋值操作又没有意义。
                                        这是一次错误的思考，我离错误远了一点。
 */
                levelTwoVOList.add(levelTwoVO);
            }
            levelOneVO.setChildren(levelTwoVOList);
            levelOneVOList.add(levelOneVO);
        }
        return levelOneVOList;
    }

    // 返数据库查来的对象数组，梭进可变数组里。
    @Override
    public List<ProductCategory> getProductCategoryById(Integer type, Integer parentId) {
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        wrapper.eq("parent_id",parentId);
        return this.productCategoryMapper.selectList(wrapper); // 这个方法返回的才是想要的东西，别怕，看 IDE 提示。
    }
}
