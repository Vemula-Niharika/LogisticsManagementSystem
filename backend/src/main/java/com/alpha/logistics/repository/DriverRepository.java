package com.alpha.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.logistics.entity.Driver;


public interface DriverRepository extends JpaRepository<Driver, Long>
{
	boolean existsByEmail(String email);

}