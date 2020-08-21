package com.fhswar.exceptions;

import com.fhswar.enums.WrongRegisteringEnums;
import org.springframework.beans.factory.annotation.Autowired;

public class WrongRegisteringException extends RuntimeException {
    // 这里构造函数的入参就是枚举类的实例,code 和 type 都是枚举类的属性。
    public WrongRegisteringException(WrongRegisteringEnums enums){ super(enums.getType()); }

//    @Autowired
//    WrongRegisteringEnums wrongRegisteringEnums; 这真是瞎 jb 乱写。
}
