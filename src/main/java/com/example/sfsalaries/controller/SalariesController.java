package com.example.sfsalaries.controller;

import com.example.sfsalaries.data.Salaries;
import com.example.sfsalaries.service.SalariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salaries")
public class SalariesController {

    private final SalariesService salariesService;

    @Autowired
    public SalariesController(SalariesService salariesService) {
        this.salariesService = salariesService;
    }

    @GetMapping("/all")
    public List<Salaries> getAllSalaries() {
        return salariesService.getAllSalaries();
    }

    @GetMapping("/list") // GET http://localhost:8080/api/salaries/?limit=10
    public List<Salaries> getSalaries(@RequestParam(name = "limit") int limit) {
        return salariesService.getSalaries(limit);
    }

    @GetMapping("/id/{id}")
    public Salaries getSalariesById(@PathVariable int id) {
        return salariesService.getSalariesById(id);
    }
/*
    @GetMapping("/qbe")
    public List<Salaries> getSalariesQBE(@RequestParam(name = "jobTitle") String jobTitle) {
        return salariesService.getSalariesQBE(jobTitle);
    }

 */

    @GetMapping("/year") // GET http://localhost:8080/api/salaries/year?minYear=2010&maxYear=2011&limit=5
    public List<Salaries> getSalariesByYearInRange(
            @RequestParam(name = "minYear", required = false) int minYear,
            @RequestParam(name = "maxYear", required = false) int maxYear,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ){
        return salariesService.getSalariesByYearInRange(minYear, maxYear, limit);
    }

    @GetMapping("/salary") // GET http://localhost:8080/api/salaries/salary?minSal=50000&maxSal=100000&limit=5
    public List<Salaries> getSalariesByTotalPayInRange(
            @RequestParam(name = "minSal", required = false) int min,
            @RequestParam(name = "maxSal", required = false) int max,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        return salariesService.getSalariesByTotalPay(min, max, limit);
    }

    @GetMapping("/search") // GET http://localhost:8080/api/salaries/search?keyword=chief&limit=20
    public List<Salaries> searchByJobTitle(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        return salariesService.searchByJobTitle(keyword, limit);
    }

    @GetMapping("/full-names")
    public List<String> getEmployeesFullNames(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        return salariesService.getEmployeeFullNames(limit);
    }

    @GetMapping("/first-names")
    public List<String> getEmployeeFirstNames(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        return salariesService.getEmployeeNames(limit);
    }

    @GetMapping("/avg-year")
    public Map<Integer, Double> getSalariesAvgByYear() {
        return salariesService.getAvgSalariesByYear();
    }

    @GetMapping("/status")
    public List<Salaries> getSalariesWithStatus(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        return salariesService.getSalariesWithStatus(limit);
    }

    @GetMapping("/names-map")
    public Map<String, Integer> getFirstNamesMap(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        return salariesService.getFirstNamesMap(limit);
    }

    @GetMapping("/avg-total-pay")
    public int getAverageTotalPay() {
        return salariesService.getAverageTotalPay();
    }

    @GetMapping("/avg-base-pay")
    public int getAverageBasePay() {
        return salariesService.getAverageBasePay();
    }

    @GetMapping("/avg-other-pay")
    public int getAverageOtherPay() {
        return salariesService.getAverageOtherPay();
    }

    @GetMapping("/avg")
    public Map<String, Integer> getAvgPays() {
        return salariesService.getAverages();
    }

}
