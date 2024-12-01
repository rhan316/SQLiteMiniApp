package com.example.sfsalaries.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "base_Pay")
    private Double basePay;

    @Column(name = "overtime_pay")
    private Double overtimePay;

    @Column(name = "other_Pay")
    private Double otherPay;

    @Column(name = "benefits")
    private Double benefits;

    @Column(name = "total_pay")
    private Double totalPay;

    @Column(name = "total_pay_benefits")
    private Double totalPayBenefits;

    @Column(name = "year")
    private Integer year;

    @Column(name = "notes")
    private String notes;

    @Column(name = "agency")
    private String agency;

    @Column(name = "status")
    private String status;
}
