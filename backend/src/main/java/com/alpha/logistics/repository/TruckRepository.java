package com.alpha.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.logistics.entity.Truck;


public interface TruckRepository extends JpaRepository<Truck, Long> 
{
	 boolean existsByNumber(String number);
}