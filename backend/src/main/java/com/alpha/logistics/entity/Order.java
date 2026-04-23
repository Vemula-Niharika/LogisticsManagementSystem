package com.alpha.logistics.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @ManyToOne
    private Cargo cargo;

    @ManyToOne
    private Address loadingAddress;

    @ManyToOne
    private Address unloadingAddress;

    @ManyToOne
    private Truck truck;

    @ManyToOne
    private Driver driver;

    private double cost;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDate orderDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Address getLoadingAddress() {
		return loadingAddress;
	}

	public void setLoadingAddress(Address loadingAddress) {
		this.loadingAddress = loadingAddress;
	}

	public Address getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(Address unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
    
    

    
    
}