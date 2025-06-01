package com.vis.accountingvis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vis.accountingvis.entity.Category;
import com.vis.accountingvis.mapper.CategoryMapper;
import com.vis.accountingvis.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public Category getCategoryByName(String name) {
        // CategoryMapper 中已定义 selectCategoryByName
        // return baseMapper.selectCategoryByName(name);
        // 或者使用 QueryWrapper
        // 注意 Category 实体中的字段名是 category_name
        return getOne(new QueryWrapper<Category>().eq("category_name", name));
    }
}