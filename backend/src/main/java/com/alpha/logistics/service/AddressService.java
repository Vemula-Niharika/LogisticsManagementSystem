package com.alpha.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.logistics.dto.AddressDto;
import com.alpha.logistics.entity.Address;
import com.alpha.logistics.exception.AddressNotFoundException;
import com.alpha.logistics.exception.DuplicateResourceException;
import com.alpha.logistics.repository.AddressRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(AddressDto dto) {

        log.info("Creating address for city: {}", dto.getCity());

        boolean exists =
            addressRepository.existsByCityAndStateAndPincodeAndType(
                dto.getCity(),
                dto.getState(),
                dto.getPincode(),
                dto.getType()
            );

        if (exists) {
            log.warn("Duplicate address detected for city: {}", dto.getCity());
            throw new DuplicateResourceException("Address already exists");
        }

        Address address = new Address();

        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        address.setType(dto.getType());

        Address savedAddress = addressRepository.save(address);

        log.info("Address created successfully with id: {}", savedAddress.getId());

        return savedAddress;
    }

    public Address getAddress(Long id) {

        log.info("Fetching address with id: {}", id);

        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Address not found with id: {}", id);
                    return new AddressNotFoundException("Address not found: " + id);
                });
    }

    public List<Address> getAllAddresses(){

        log.info("Fetching all addresses");

        return addressRepository.findAll();
    }
}