package com.alpha.logistics.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CargoDto {

    @NotBlank(message = "Cargo name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 1, message = "Weight must be greater than 0")
    private int weight;

    @Min(value = 1, message = "Count must be greater than 0")
    private int count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

    
}