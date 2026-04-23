package com.alpha.logistics.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.logistics.dto.*;
import com.alpha.logistics.entity.*;
import com.alpha.logistics.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private OrderService orderService;



    // ===============================
    // ADDRESS
    // ===============================
    @PostMapping("/addresses")
    public ResponseEntity<ResponseStructure<Address>> createAddress(
            @Valid @RequestBody AddressDto dto) {

        Address address = addressService.createAddress(dto);

        ResponseStructure<Address> response =
                new ResponseStructure<>(
                        HttpStatus.CREATED.value(),
                        "Address created",
                        address
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    // ===============================
    // TRUCK
    // ===============================
    @PostMapping("/trucks")
    public ResponseEntity<ResponseStructure<Truck>> createTruck(
            @Valid @RequestBody TruckDto dto) {

        Truck truck = truckService.createTruck(dto);

        ResponseStructure<Truck> response =
                new ResponseStructure<>(
                        HttpStatus.CREATED.value(),
                        "Truck created",
                        truck
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    // ===============================
    // DRIVER
    // ===============================
    @PostMapping("/drivers")
    public ResponseEntity<ResponseStructure<Driver>> createDriver(
            @Valid @RequestBody DriverDto dto) {

        Driver driver = driverService.createDriver(dto);

        ResponseStructure<Driver> response =
                new ResponseStructure<>(
                        HttpStatus.CREATED.value(),
                        "Driver created",
                        driver
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    // ===============================
    // ASSIGN TRUCK
    // ===============================
    @PutMapping("/orders/{orderId}/truck/{truckId}")
    public ResponseEntity<ResponseStructure<Order>> assignTruck(
            @PathVariable Long orderId,
            @PathVariable Long truckId) {

        Order order = orderService.assignTruck(orderId, truckId);

        ResponseStructure<Order> response =
                new ResponseStructure<>(
                        HttpStatus.OK.value(),
                        "Truck assigned successfully",
                        order
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    // ===============================
    // ASSIGN DRIVER
    // ===============================
    @PutMapping("/orders/{orderId}/driver/{driverId}")
    public ResponseEntity<ResponseStructure<Order>> assignDriver(
            @PathVariable Long orderId,
            @PathVariable Long driverId) {

        Order order = orderService.assignDriver(orderId, driverId);

        ResponseStructure<Order> response =
                new ResponseStructure<>(
                        HttpStatus.OK.value(),
                        "Driver assigned successfully",
                        order
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    // ===============================
    // COMPLETE ORDER
    // ===============================
    @PutMapping("/orders/{orderId}/complete")
    public ResponseEntity<ResponseStructure<Order>> completeOrder(
            @PathVariable Long orderId) {

        Order order = orderService.completeOrder(orderId);

        ResponseStructure<Order> response =
                new ResponseStructure<>(
                        HttpStatus.OK.value(),
                        "Order completed successfully",
                        order
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/addresses")
    public ResponseEntity<ResponseStructure<List<Address>>> getAllAddresses(){

    List<Address> addresses = addressService.getAllAddresses();

    ResponseStructure<List<Address>> response =
            new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Addresses fetched successfully",
                    addresses
            );

    return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/trucks")
    public ResponseEntity<ResponseStructure<List<Truck>>> getAllTrucks(){

    List<Truck> trucks = truckService.getAllTrucks();

    ResponseStructure<List<Truck>> response =
            new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Trucks fetched successfully",
                    trucks
            );

    return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/drivers")
    public ResponseEntity<ResponseStructure<List<Driver>>> getAllDrivers(){

    List<Driver> drivers = driverService.getAllDrivers();

    ResponseStructure<List<Driver>> response =
            new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Drivers fetched successfully",
                    drivers
            );

    return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/orders")
    public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders(){

    List<Order> orders = orderService.getAllOrders();

    ResponseStructure<List<Order>> response =
            new ResponseStructure<>(
                    HttpStatus.OK.value(),
                    "Orders fetched successfully",
                    orders
            );

    return new ResponseEntity<>(response, HttpStatus.OK);
    }

}