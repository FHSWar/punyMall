package com.fhswar.enums;

import lombok.Getter;

// 为运行时错误设计错误类型，统一错误，降低开发时沟通成本
@Getter
public enum WrongRegisteringEnums {
    // 以下三个都是该枚举类的实例。最后一个枚举用分号，否则只能用逗号。
    USER_NOT_EXIST(0, "用户不存在"),
    ORDER_NOT_EXIST(1,"订单不存在"),
    USER_SAVE_FAIL(2,"用户创建失败");


    // enum 的 fancy 用法。注意到 enum 类的构造器修饰符只能是 private，无需显式声明。
    WrongRegisteringEnums(Integer code, String type){
        this.code = code;
        this.type = type;
    }

    // 枚举类型属性私有！！！
    private final Integer code;
    private final String type;
}
