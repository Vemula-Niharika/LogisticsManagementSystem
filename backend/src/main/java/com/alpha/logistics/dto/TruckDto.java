package com.alpha.logistics.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TruckDto {

    @NotBlank(message = "Truck number is required")
    private String number;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
    
    
    
    

}