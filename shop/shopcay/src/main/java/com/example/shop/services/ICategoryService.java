package com.example.shop.services;

import com.example.shop.dtos.CategoryDTO;
import com.example.shop.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO categoryDTO);
void  deleteCategory(long id);
}
