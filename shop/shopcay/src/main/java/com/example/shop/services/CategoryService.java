package com.example.shop.services;

import com.example.shop.dtos.CategoryDTO;
import com.example.shop.models.Category;
import com.example.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService  implements ICategoryService{
   private final CategoryRepository categoryRepository;



    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
Category newCategory = Category.builder()
        .name(categoryDTO.getName())

        .build();
return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));


    }

    @Override
    public List<Category> getAllCategories() {


        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {

        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;

    }

    @Override
    public void deleteCategory(long id) {
        Category existingCategory = getCategoryById(id);


        categoryRepository.delete(existingCategory);
        
    }
}
