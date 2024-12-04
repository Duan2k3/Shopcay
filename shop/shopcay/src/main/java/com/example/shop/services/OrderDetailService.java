package com.example.shop.services;

import com.example.shop.dtos.OrderDetailDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Order;
import com.example.shop.models.OrderDetail;
import com.example.shop.models.Product;
import com.example.shop.repositories.OrderDetailRepository;
import com.example.shop.repositories.OrderRepository;
import com.example.shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService  implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderID()).orElseThrow(() -> new DataNotFoundException("Cannot find order with id = " + orderDetailDTO.getOrderID()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id = " + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(orderDetailDTO.getQuantity())
                .productPrice(orderDetailDTO.getProductPrice())
                .productImage(product.getImage())
                .productName(orderDetailDTO.getProductName())


                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find orderDetail with id = " + id));


    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find orderdetail with id = " + id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));

        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setQuantity(orderDetailDTO.getQuantity());
        existingOrderDetail.setProductPrice(orderDetailDTO.getProductPrice());
        return orderDetailRepository.save(existingOrderDetail);

    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
        public List<OrderDetail> findByOrderId (Long orderId){
        return orderDetailRepository.findByOrderId(orderId);
        }
    }






