package com.vis.accountingvis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("category")
public class Category {
    // @TableId 的 value 属性应指向数据库中的实际列名
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId; // 修改为驼峰命名

    // 如果数据库列名是 category_name，MyBatis Plus 在开启驼峰转换后会自动映射，
    // 或者可以通过 @TableField(value = "category_name") 显式指定
    private String categoryName; // 修改为驼峰命名

    // Getters and Setters (遵循 JavaBean 规范)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}