package com.example.shop.controllers;

import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.ProductDTO;
import com.example.shop.models.Product;
import com.example.shop.responses.ProductListResponse;
import com.example.shop.responses.ProductResponse;
import com.example.shop.services.IProductService;
import com.example.shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;
    private final LocalizationUtil localizationUtil;

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAllProduct(
           @RequestParam(defaultValue = "") String keyword,
           @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page,limit, Sort.by("id").ascending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(keyword,categoryId,pageRequest);
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                 .products(products)
                .totalPages(totalPage)

                .build());
    }


    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestPart("product") ProductDTO productDTO,
            BindingResult result,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(localizationUtil.getLocalizedMessage(MessageKeys.INSERT_PRODUCT_FAILED));
        }

        try {
            // Lưu ảnh và lấy đường dẫn
            String imageUrl = productService.storeImage(imageFile);
            productDTO.setImage(imageUrl);  // Gán đường dẫn ảnh cho ProductDTO

            // Tạo sản phẩm mới
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateProduct(
            @PathVariable long id,
            @RequestBody ProductDTO productDTO
    ){
       try {
           Product updatedProduct = productService.updateProduct(id,productDTO);
               return ResponseEntity.ok(updatedProduct);
       }
       catch (Exception e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
      try {
          productService.deleteProduct(id);
          return ResponseEntity.ok(String.format("Product with id = %d deleted successfully",id));
      }
      catch (Exception e){
          return  ResponseEntity.badRequest().body(e.getMessage());
      }
    }
}
