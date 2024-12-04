package com.example.shop.controllers;

import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.CategoryDTO;
import com.example.shop.models.Category;
import com.example.shop.responses.CategoryResponse;
import com.example.shop.responses.UpdateCategoryResponse;
import com.example.shop.services.CategoryService;
import com.example.shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtil localizationUtil;
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) {

       List<Category> categories = categoryService.getAllCategories();
       return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategories(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
            ) {
        CategoryResponse categoryResponse = new CategoryResponse();
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .toList();

            categoryResponse.setErrors(errorMessage);


            return ResponseEntity.badRequest().body(categoryResponse);


        }

       // Category category = categoryService.createCategory(categoryDTO);
        Category category = categoryService.createCategory(categoryDTO);

        categoryResponse.setMessage(localizationUtil.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
        categoryResponse.setCategory(category);


        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO

    ){

        UpdateCategoryResponse  updateCategoryResponse = new UpdateCategoryResponse();
        categoryService.updateCategory(id,categoryDTO);
        updateCategoryResponse.setMessage("Update category successfully with name = " +categoryDTO);
return ResponseEntity.ok(updateCategoryResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable long id){
      categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category successfully");

    }
}
