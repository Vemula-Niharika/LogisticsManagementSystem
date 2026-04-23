package com.alpha.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.logistics.dto.DriverDto;
import com.alpha.logistics.entity.Driver;
import com.alpha.logistics.exception.DriverNotFoundException;
import com.alpha.logistics.exception.DuplicateResourceException;
import com.alpha.logistics.repository.DriverRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DriverService {

    private static final Logger log = LoggerFactory.getLogger(DriverService.class);

    @Autowired
    private DriverRepository driverRepository;

    public Driver createDriver(DriverDto dto) {

        log.info("Creating driver with email: {}", dto.getEmail());

        // Duplicate email check
        if (driverRepository.existsByEmail(dto.getEmail())) {
            log.warn("Driver already exists with email: {}", dto.getEmail());
            throw new DuplicateResourceException(
                    "Driver already exists with email: " + dto.getEmail());
        }

        Driver driver = new Driver();

        driver.setName(dto.getName());
        driver.setEmail(dto.getEmail());
        driver.setPhone(dto.getPhone());

        Driver savedDriver = driverRepository.save(driver);

        log.info("Driver created successfully with id: {}", savedDriver.getId());

        return savedDriver;
    }

    public Driver getDriver(Long id) {

        log.info("Fetching driver with id: {}", id);

        return driverRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Driver not found with id: {}", id);
                    return new DriverNotFoundException(
                            "Driver not found with id: " + id);
                });
    }

    public List<Driver> getAllDrivers(){

        log.info("Fetching all drivers");

        return driverRepository.findAll();
    }
}