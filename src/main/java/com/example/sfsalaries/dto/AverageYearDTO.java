package com.example.sfsalaries.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageYearDTO {
	private int year;
	private double amount;

	public AverageYearDTO(int year, double amount) {
		this.year = year;
		this.amount = amount;
	}
}
