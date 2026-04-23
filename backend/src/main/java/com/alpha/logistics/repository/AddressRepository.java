package com.alpha.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.logistics.entity.Address;
import com.alpha.logistics.entity.AddressType;


public interface AddressRepository extends JpaRepository<Address, Long> 
{


	    boolean existsByCityAndStateAndPincodeAndType
	    (
	        String city,
	        String state,
	        String pincode,
	        AddressType type
	    );
	
}