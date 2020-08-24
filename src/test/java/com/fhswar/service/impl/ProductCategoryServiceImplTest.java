package com.fhswar.service.impl;

import com.fhswar.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 这是手动加的
class ProductCategoryServiceImplTest {
    @Autowired // 这是手动加的
    private ProductCategoryService service;

    @Test
    void getAllProductCategory() {
        service.getAllProductCategoryVO(); // 这行内容是手动写的
    }

    @Test
    void getProductCategoryById() {
    }
}