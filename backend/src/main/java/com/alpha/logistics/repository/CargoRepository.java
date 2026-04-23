package com.alpha.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.logistics.entity.Cargo;


public interface CargoRepository extends JpaRepository<Cargo, Long> {
	
}