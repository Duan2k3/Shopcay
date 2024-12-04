package com.example.shop.controllers;

import com.example.shop.components.LocalizationUtil;
import com.example.shop.dtos.OrderDetailDTO;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.OrderDetail;
import com.example.shop.responses.OrderDetailResponse;
import com.example.shop.services.IOrderDetailService;
import com.example.shop.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDatailController {

    private final IOrderDetailService orderDetailService;
    private final LocalizationUtil localizationUtil;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable Long id
    ) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
      return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
    try {


        OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    }
    catch (Exception e){
        return ResponseEntity.ok().body(e.getMessage());
    }

    }


    @PutMapping("/{id}")
    public ResponseEntity<?>updateOrderDatail(
            @Valid @PathVariable Long id,
            @RequestBody OrderDetailDTO orderDetailDTO

    ){
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id,orderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        }
        catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(
            @Valid @PathVariable("id") Long id
    ){
        orderDetailService.deleteById(id);
        return ResponseEntity.ok().body(localizationUtil.getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY));

            }
}
