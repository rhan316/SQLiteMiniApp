package com.example.sfsalaries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageDTO {
    private double total_pay;
    private double base_pay;
    private double other_pay;

    public AverageDTO(double total_pay, double base_pay, double other_pay) {
        this.total_pay = total_pay;
        this.base_pay = base_pay;
        this.other_pay = other_pay;
    }
}
