package com.alpha.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.logistics.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}