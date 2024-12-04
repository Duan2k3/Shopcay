package com.example.shop.controllers;

import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.OrderDTO;
import com.example.shop.models.Order;
import com.example.shop.services.IOrderService;
import com.example.shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final LocalizationUtil localizationUtil;


    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(
                @Valid @PathVariable("user_id") Long userId
    ) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
            ) {
        try {


            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order orderResponse = orderService.createOrder(orderDTO);
             return ResponseEntity.ok(orderResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
         }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            @Valid @PathVariable Long id
    ){
       try {
           Order order = orderService.updateOrder(id,orderDTO);
           return ResponseEntity.ok(order);

       }
       catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @Valid @PathVariable Long id
    ){
       orderService.deleteOrder(id);
       return ResponseEntity.ok(localizationUtil.getLocalizedMessage(MessageKeys.DELETE_ORDER_SUCCESSFULLY));
    }
}
