package com.alpha.logistics.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alpha.logistics.dto.OrderDto;
import com.alpha.logistics.entity.Address;
import com.alpha.logistics.entity.AddressType;
import com.alpha.logistics.entity.Cargo;
import com.alpha.logistics.entity.Driver;
import com.alpha.logistics.entity.EmailType;
import com.alpha.logistics.entity.Order;
import com.alpha.logistics.entity.OrderStatus;
import com.alpha.logistics.entity.Truck;

import com.alpha.logistics.exception.AddressNotFoundException;
import com.alpha.logistics.exception.DriverNotFoundException;
import com.alpha.logistics.exception.OrderNotFoundException;
import com.alpha.logistics.exception.TruckNotFoundException;

import com.alpha.logistics.repository.AddressRepository;
import com.alpha.logistics.repository.CargoRepository;
import com.alpha.logistics.repository.DriverRepository;
import com.alpha.logistics.repository.OrderRepository;
import com.alpha.logistics.repository.TruckRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private static final double RATE_PER_KG = 20;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private EmailService emailService;


    // ===============================
    // 1️⃣ PLACE ORDER (USER)
    // ===============================
    public Order placeOrder(OrderDto dto) {

        

        // Fetch Addresses
        Address loading = addressRepository.findById(dto.getLoadingAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException("Loading address not found"));

        Address unloading = addressRepository.findById(dto.getUnloadingAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException("Unloading address not found"));
        
        if (loading.getCity().equalsIgnoreCase(unloading.getCity())) {
            throw new IllegalArgumentException("Loading and Unloading city cannot be same");
        }

     // VALIDATE ADDRESS TYPE
        if (loading.getType() != AddressType.LOADING) {
            throw new IllegalArgumentException("Selected loading address is invalid");
        }

        if (unloading.getType() != AddressType.UNLOADING) {
            throw new IllegalArgumentException("Selected unloading address is invalid");
        }
        // Create Cargo
        Cargo cargo = new Cargo();

        cargo.setName(dto.getCargoName());
        cargo.setDescription(dto.getDescription());
        cargo.setWeight(dto.getWeight());
        cargo.setCount(dto.getCount());

        cargo = cargoRepository.save(cargo);
        
        
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("Placing order for user {}", email);


        // Create Order
        Order order = new Order();

        order.setCargo(cargo);
        order.setLoadingAddress(loading);
        order.setUnloadingAddress(unloading);
        order.setUserEmail(email);
        
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDate.now());


        Order savedOrder = orderRepository.save(order);

        log.info("Order placed successfully with id {}", savedOrder.getId());

        // Send Email
        emailService.sendMail(
        		
        		        email,
                EmailType.ORDER_PLACED,
                null
        );

        return savedOrder;
    }


    // ===============================
    // 2️⃣ ASSIGN TRUCK (ADMIN)
    // ===============================
    public Order assignTruck(Long orderId, Long truckId) {

        log.info("Assigning truck {} to order {}", truckId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found: " + orderId));


        if (order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalStateException("Order is not ready for truck assignment");
        }


        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() ->
                        new TruckNotFoundException("Truck not found: " + truckId));
        
        if (!truck.isAvailable()) {
            throw new RuntimeException("Truck is already assigned");
        }


        // Capacity Check
        int totalWeight =
                order.getCargo().getWeight() *
                order.getCargo().getCount();


        if (totalWeight > truck.getCapacity()) {
            throw new IllegalArgumentException("Truck capacity is insufficient");
        }


        // Cost Calculation
        double cost = totalWeight * RATE_PER_KG;

        log.info("Calculated cost for order {} is ₹{}", orderId, cost);


        order.setTruck(truck);
        
        truck.setAvailable(false);
        truckRepository.save(truck);
        order.setCost(cost);
        order.setStatus(OrderStatus.TRUCK_ASSIGNED);


        Order updatedOrder = orderRepository.save(order);

        log.info("Truck {} assigned successfully to order {}", truckId, orderId);


        // Send Email
        emailService.sendMail(
                order.getUserEmail(),
                EmailType.TRUCK_ASSIGNED,
                "Estimated Cost: ₹" + cost
        );

        return updatedOrder;
    }


    // ===============================
    // 3️⃣ ASSIGN DRIVER (ADMIN)
    // ===============================
    public Order assignDriver(Long orderId, Long driverId) {

        log.info("Assigning driver {} to order {}", driverId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found: " + orderId));


        if (order.getStatus() != OrderStatus.TRUCK_ASSIGNED) {
            throw new IllegalStateException("Assign truck before driver");
        }


        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new DriverNotFoundException("Driver not found: " + driverId));
        
        if (!driver.isAvailable()) {
            throw new RuntimeException("Driver is already assigned");
        }


        order.setDriver(driver);
        
        driver.setAvailable(false);
        driverRepository.save(driver);
        order.setStatus(OrderStatus.DRIVER_ASSIGNED);


        Order updatedOrder = orderRepository.save(order);

        log.info("Driver {} assigned successfully to order {}", driverId, orderId);


        // Send Email
        emailService.sendMail(
                order.getUserEmail(),
                EmailType.DRIVER_ASSIGNED,
                null
        );

        return updatedOrder;
    }


    // ===============================
    // 4️⃣ COMPLETE ORDER (ADMIN/SYSTEM)
    // ===============================
    public Order completeOrder(Long orderId) {

        log.info("Completing order {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found: " + orderId));


        if (order.getStatus() != OrderStatus.DRIVER_ASSIGNED) {
            throw new IllegalStateException("Driver must be assigned first");
        }


        order.setStatus(OrderStatus.COMPLETED);


        Order completedOrder = orderRepository.save(order);

        log.info("Order {} completed successfully", orderId);


        // Send Email
        emailService.sendMail(
                order.getUserEmail(),
                EmailType.ORDER_COMPLETED,
                "Final Cost: ₹" + order.getCost()
        );
        Truck truck = order.getTruck();
        Driver driver = order.getDriver();

        if (truck != null) {
            truck.setAvailable(true);
            truckRepository.save(truck);
        }

        if (driver != null) {
            driver.setAvailable(true);
            driverRepository.save(driver);
        }

        return completedOrder;
    }


    // ===============================
    // 5️⃣ GET ALL ORDERS
    // ===============================
    public List<Order> getAllOrders(){

        log.info("Fetching all orders");

        return orderRepository.findAll();
    }
}