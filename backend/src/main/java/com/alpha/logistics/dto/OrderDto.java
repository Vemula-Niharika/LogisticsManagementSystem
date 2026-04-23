package com.alpha.logistics.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class OrderDto {

	@NotBlank(message = "Cargo name is required")
	private String cargoName;

	@NotBlank(message = "Description is required")
	private String description;

	@NotNull(message = "Weight is required")
	@Min(value = 1, message = "Weight must be at least 1 kg")
	private Integer weight;

	@NotNull(message = "Count is required")
	@Min(value = 1, message = "Count must be at least 1")
	private Integer count;

    @NotNull(message = "Loading address ID is required")
    private Long loadingAddressId;

    @NotNull(message = "Unloading address ID is required")
    private Long unloadingAddressId;


     
    

	

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getLoadingAddressId() {
		return loadingAddressId;
	}

	public void setLoadingAddressId(Long loadingAddressId) {
		this.loadingAddressId = loadingAddressId;
	}

	public Long getUnloadingAddressId() {
		return unloadingAddressId;
	}

	public void setUnloadingAddressId(Long unloadingAddressId) {
		this.unloadingAddressId = unloadingAddressId;
	}

	

//    @NotNull(message = "Driver ID is required")
//    private Long driverId;

//    private LocalDate orderDate; // Optional, default to today in service
    
    
    
    

}