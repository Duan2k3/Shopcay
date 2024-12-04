package com.example.shop.services;

import com.example.shop.dtos.OrderDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Order;
import com.example.shop.models.User;
import com.example.shop.repositories.OrderDetailRepository;
import com.example.shop.repositories.OrderRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;



    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception{
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find user with id = " + orderDTO.getUserId()));

        Order order = Order.builder()
               .user(user)
                .note(orderDTO.getNote())
                .shippingMethod(orderDTO.getShippingMethod())
                .fullName(orderDTO.getFullName())
                .address(orderDTO.getAddress())
                .phoneNumber(orderDTO.getPhoneNumber())

                .status(orderDTO.getStatus())
                .paymentMethod(orderDTO.getPaymentMethod())

                .build();

        return orderRepository.save(order);

    }

    @Override
    public Order getOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return order;

    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {



        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find order with id = " + id));

        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("Cannot find user with id = " + orderDTO.getUserId()));


        order.setAddress(orderDTO.getAddress());
        order.setNote(orderDTO.getNote());
        order.setFullName(orderDTO.getFullName());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setShippingMethod(orderDTO.getShippingMethod());

        return orderRepository.save(order);


    }

    @Override
    public void deleteOrder(Long id) {
Order order = orderRepository.findById(id).orElse(null);
if (order != null){
    orderRepository.delete(order);
    orderRepository.save(order);

}
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
