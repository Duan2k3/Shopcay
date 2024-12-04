package com.example.shop.repositories;

import com.example.shop.models.Category;
import com.example.shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Kiểm tra sự tồn tại của sản phẩm dựa trên tên
    boolean existsByName(String name);

    // Phân trang tất cả các sản phẩm
    Page<Product> findAll(Pageable pageable);

    // Tìm kiếm sản phẩm theo categoryId và keyword trong tên hoặc mô tả
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword, Pageable pageable);

    // Lấy chi tiết của sản phẩm dựa trên productId, không cần JOIN với productImages nữa
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> getDetailProduct(@Param("productId") Long productId);

    // Lấy danh sách các sản phẩm dựa trên danh sách productIds
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
}
