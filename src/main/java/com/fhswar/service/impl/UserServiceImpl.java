package com.fhswar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fhswar.entity.User;
import com.fhswar.enums.GenderEnum;
import com.fhswar.mapper.UserMapper;
import com.fhswar.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fhswar
 * @since 2020-08-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper; // 若不在 mapper 接口加 repository 接口，这里会标红。

    @Override
    public boolean checkGender(User user) {
        if(user.getSex() == 0){
            user.setGender(GenderEnum.Male);
        } else if(user.getSex() == 1){
            user.setGender(GenderEnum.FeMale);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertIntoDB(User user) { // service 层只写逻辑，校验交给 controller
        int number = this.userMapper.insert(user);
        if (number != 1) return false;
        return true;
    }

    @Override
    public User checkUser(User user) { // QueryWrapper 的用法要熟悉起来。
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // wrapper就是封装器，把一个个条件加到 wrapper 里再去查询，就实现了多条件查询。嗨呀面向对象是真的方便！
        // 这里用的是数据库里的字段名，不能用驼峰名。想来也合理，这是去问数据库查东西嘛。
        wrapper.eq("login_name", user.getLoginName());
        wrapper.eq("password", user.getPassword());
        return this.userMapper.selectOne(wrapper);
    }
}
