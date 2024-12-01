package com.example.sfsalaries.data;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class SimpleSalaries {
    private Long id;
    private String employeeName;
    private String jobTitle;
    private BigDecimal totalPay;
    private Integer year;
}
