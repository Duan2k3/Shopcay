package com.example.shop.services;

import com.example.shop.dtos.ProductDTO;
import com.example.shop.models.Product;
import com.example.shop.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getAllProductById(long id) throws Exception;
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    Product updateProduct(long id , ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean exisByName(String name);

    List<Product> findProductByIds(List<Long> productIds);


    String storeImage(MultipartFile file) throws Exception;
}
