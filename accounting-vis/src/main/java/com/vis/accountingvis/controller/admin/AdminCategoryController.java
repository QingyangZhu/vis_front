package com.vis.accountingvis.controller.admin;

import com.vis.accountingvis.entity.Category;
import com.vis.accountingvis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // 可选：检查类别名称是否已存在
        if (categoryService.getCategoryByName(category.getCategoryName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        boolean saved = categoryService.save(category);
        return saved ? ResponseEntity.status(HttpStatus.CREATED).body(category) : ResponseEntity.internalServerError().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getById(id); // Category 实体的主键是 category_id
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category categoryDetails) {
        if (categoryDetails.getCategoryName() == null || categoryDetails.getCategoryName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Category existingCategory = categoryService.getById(id);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        // 检查更新后的名称是否与其他类别冲突（排除自身）
        Category categoryWithSameName = categoryService.getCategoryByName(categoryDetails.getCategoryName());
        if (categoryWithSameName != null && !categoryWithSameName.getCategoryId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // category_id 是主键，应通过路径变量设置，而不是请求体中的 category_id (如果存在)
        // categoryDetails.setCategory_id(id); // 确保ID正确
        // Mybatis Plus updateById 会根据实体类中的 @TableId 字段来更新
        existingCategory.setCategoryName(categoryDetails.getCategoryName()); // 只更新名称

        boolean updated = categoryService.updateById(existingCategory); // 更新 existingCategory
        return updated ? ResponseEntity.ok(categoryService.getById(id)) : ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        // 考虑: 删除类别前是否需要检查是否有公司关联此类别
        boolean deleted = categoryService.removeById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}