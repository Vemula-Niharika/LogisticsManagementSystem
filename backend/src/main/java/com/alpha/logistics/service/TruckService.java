package com.alpha.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.logistics.dto.TruckDto;
import com.alpha.logistics.entity.Truck;
import com.alpha.logistics.exception.DuplicateResourceException;
import com.alpha.logistics.exception.TruckNotFoundException;
import com.alpha.logistics.repository.TruckRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TruckService {

    private static final Logger log = LoggerFactory.getLogger(TruckService.class);

    @Autowired
    private TruckRepository truckRepository;

    public Truck createTruck(TruckDto dto) {

        log.info("Creating truck with number {}", dto.getNumber());

        // Duplicate truck number
        if (truckRepository.existsByNumber(dto.getNumber())) {

            log.warn("Truck already exists with number {}", dto.getNumber());

            throw new DuplicateResourceException(
                    "Truck already exists with number: " + dto.getNumber());
        }

        Truck truck = new Truck();

        truck.setNumber(dto.getNumber());
        truck.setCapacity(dto.getCapacity());

        Truck savedTruck = truckRepository.save(truck);

        log.info("Truck created successfully with id {}", savedTruck.getId());

        return savedTruck;
    }

    public Truck getTruck(Long id) {

        log.info("Fetching truck with id {}", id);

        return truckRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Truck not found with id {}", id);
                    return new TruckNotFoundException(
                            "Truck not found with id: " + id);
                });
    }

    public List<Truck> getAllTrucks(){

        log.info("Fetching all trucks");

        return truckRepository.findAll();
    }
}