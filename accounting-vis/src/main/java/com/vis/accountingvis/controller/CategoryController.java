package com.vis.accountingvis.controller;

import com.vis.accountingvis.entity.Category;
import com.vis.accountingvis.mapper.CategoryMapper;
import com.vis.accountingvis.service.CategoryService; // 保持风格，注入Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import com.vis.accountingvis.mapper.CategoryMapper; // 或者直接注入Mapper，取决于项目统一风格

import java.util.List;

@RestController
@RequestMapping("/categories") // 定义此 Controller 的基础请求路径为 /categories
public class CategoryController {

    //private final CategoryService categoryService;
    private final CategoryMapper categoryMapper; // 如果选择直接注入Mapper

    @Autowired
    public CategoryController(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper; // 构造函数注入Service
    }

    /**
     * 获取所有公司类别列表。
     * 当客户端发起 GET 请求到 /categories 路径时，此方法将被调用。
     * @return 包含所有公司类别的列表。
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryMapper.selectList(null);
        // 如果直接注入Mapper，则调用 categoryMapper.selectList(null);
        // return categoryMapper.selectList(null);
    }

    // 如果未来需要根据ID或其他条件查询单个类别，可以添加类似如下的方法：
    /*
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.getById(id);
        // หรือ return categoryMapper.selectById(id);
    }
    */
    @GetMapping("/{name}")
    public Category getCategoryByName(@PathVariable String name){
        return categoryMapper.selectCategoryByName(name);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id){
        return categoryMapper.selectCategoryById(id);
    }

}