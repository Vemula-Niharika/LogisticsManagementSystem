package com.alpha.logistics.controller;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.logistics.dto.OrderDto;
import com.alpha.logistics.dto.ResponseStructure;
import com.alpha.logistics.entity.Address;
import com.alpha.logistics.entity.Order;
import com.alpha.logistics.service.AddressService;
import com.alpha.logistics.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;
    // ===============================
    // PLACE ORDER
    // ===============================
    @PostMapping("/orders")
    public ResponseEntity<ResponseStructure<Order>> placeOrder(
            @Valid @RequestBody OrderDto dto) {

        Order order = orderService.placeOrder(dto);

        ResponseStructure<Order> response =
                new ResponseStructure<>(
                        HttpStatus.CREATED.value(),
                        "Order placed successfully",
                        order
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/addresses")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddresses() {

        List<Address> addresses = addressService.getAllAddresses();

        ResponseStructure<List<Address>> response =
                new ResponseStructure<>(
                        HttpStatus.OK.value(),
                        "Addresses fetched successfully",
                        addresses
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}