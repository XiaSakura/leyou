package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryByParentId(Long pid);

    List<Category> queryByBrandId(Long bid);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Long id);
}
