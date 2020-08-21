package com.fhswar.service;

import com.fhswar.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
public interface UserService extends IService<User> { // 接口类里的接口只能是 public 修饰,所以没必要加
    boolean checkGender(User user);
    boolean insertIntoDB(User user);
    User checkUser(User user);
}
