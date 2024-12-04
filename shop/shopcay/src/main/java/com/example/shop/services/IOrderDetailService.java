package com.example.shop.services;

import com.example.shop.dtos.OrderDetailDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id , OrderDetailDTO orderDetailDTO)
                                    throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);

}
