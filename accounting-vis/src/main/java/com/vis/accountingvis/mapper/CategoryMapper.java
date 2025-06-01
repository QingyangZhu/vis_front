package com.vis.accountingvis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    // BaseMapper 提供了基础的 CRUD 方法。
    // 如果未来有针对 Category 表的特殊 SQL 查询，可以在此定义方法，
    // 并在对应的 CategoryMapper.xml 文件中实现。
    Category selectCategoryById(Integer id);
    Category selectCategoryByName(String name);
}