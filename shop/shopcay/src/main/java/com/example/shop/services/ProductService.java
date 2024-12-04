package com.example.shop.services;

import com.example.shop.dtos.ProductDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Category;
import com.example.shop.models.Product;
import com.example.shop.repositories.CategoryRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Path imageStoragePath = Paths.get("images");

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryID())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id :" + productDTO.getCategoryID()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .promotionPrice(productDTO.getPromotionPrice())
                .image(productDTO.getImage())  // Lưu đường dẫn ảnh vào sản phẩm
                .originalPrice(productDTO.getOriginalPrice())
                .description(productDTO.getDescription())
                .soldCount(productDTO.getSoldCount())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }


    @Override
    public Product getAllProductById(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.getDetailProduct(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id = " + id);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        Page<Product> productPage;
        productPage = productRepository.searchProducts(categoryId,keyword,pageRequest);
        return productPage.map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception{
        Product existingProduct = getAllProductById(id);
        if (existingProduct != null){
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryID())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find category id : " + productDTO.getCategoryID()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setImage(productDTO.getImage());
            existingProduct.setCategory(existingCategory);
            existingProduct.setDescription(existingProduct.getDescription());
            existingProduct.setQuantity(productDTO.getQuantity());
           existingProduct.setSoldCount(productDTO.getSoldCount());
           existingProduct.setOriginalPrice(productDTO.getOriginalPrice());
           existingProduct.setPromotionPrice(productDTO.getPromotionPrice());
           return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
Optional<Product> optionalProduct = productRepository.findById(id);
optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean exisByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

    public String storeImage(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File ảnh không được để trống");
        }
        if (!Files.exists(imageStoragePath)) {
            Files.createDirectories(imageStoragePath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = imageStoragePath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();  // Trả về đường dẫn tương đối
    }
}
