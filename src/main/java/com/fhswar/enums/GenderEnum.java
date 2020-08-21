package com.fhswar.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum GenderEnum {
    Male(0,"男"),
    FeMale(1,"女");

    GenderEnum(Integer num, String gender){
        this.num = num;
        this.gender = gender;
    }

    @EnumValue // 用这个注解后，配合上 yml 配置文件对应的配置，数据库存值就会存这个 num 值。
    private final Integer num;
    private final String gender;
}
