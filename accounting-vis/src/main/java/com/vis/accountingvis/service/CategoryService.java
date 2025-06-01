package com.vis.accountingvis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vis.accountingvis.entity.Category;

public interface CategoryService extends IService<Category> {
    Category getCategoryByName(String name); // 新增方法声明
}