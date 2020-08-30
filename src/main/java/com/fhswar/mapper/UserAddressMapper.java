package com.fhswar.mapper;

import com.fhswar.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    public int clearDefault(Integer userId);
}
