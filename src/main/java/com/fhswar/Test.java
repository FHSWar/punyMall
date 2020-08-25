package com.fhswar;

public class Test {
    // 字符串和数字的转换
    public static void main(String[] args) {
        String a = "333"; // 字符串包含数字时会抛异常
        Integer i = null;
        if(a != null){
            i = Integer.valueOf(a);
        }
        System.out.println(i);
    }
}
