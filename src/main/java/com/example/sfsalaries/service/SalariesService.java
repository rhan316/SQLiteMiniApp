package com.example.sfsalaries.service;

import com.example.sfsalaries.data.Salaries;
import com.example.sfsalaries.dto.AverageDTO;
import com.example.sfsalaries.dto.AverageYearDTO;
import com.example.sfsalaries.dto.FirstNamesDTO;
import com.example.sfsalaries.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SalariesService {

    private final SalariesRepository salariesRepository;

    @Autowired
    public SalariesService(SalariesRepository salariesRepository) {
        this.salariesRepository = salariesRepository;
    }

    public List<Salaries> getAllSalaries() {
        return salariesRepository.findAll();
    }

    public List<Salaries> getSalaries(int limit) {
        return salariesRepository.findAll(createPageable(limit)).getContent();
    }

    public List<Salaries> searchByJobTitle(String keyword, int limit) {
        return salariesRepository.findByJobTitleContainingIgnoreCase(
                keyword, createPageable(limit, "jobTitle")).getContent();
    }

    public List<Salaries> getSalariesByYearInRange(int min, int max, int limit) {
        if (numbersValidate(min, max)) {
            return salariesRepository.findByYearBetween(min, max, createPageable(limit)).getContent();
        } else {
            return getSalaries(limit);
        }
    }

    public Map<Integer, Double> getAvgSalariesByYear() {
        return salariesRepository.findAvgByYear().stream()
                .collect(Collectors.toMap(
						AverageYearDTO::getYear,
						AverageYearDTO::getAmount,
                        Double::sum
                ));
    }

    public List<String> getEmployeeFullNames(int limit) {
        return salariesRepository.findEmployeeFullNames()
                .stream()
                .limit(limit)
                .toList();
    }

    public List<String> getEmployeeNames(int limit) {
        return salariesRepository.findEmployeeFullNames()
                .stream()
                .map(this::getFirstName)
                .limit(limit)
                .toList();
    }

    public Map<String, Integer> getFirstNamesMap(int limit) {
        List<Object[]> results = salariesRepository.findFirstNamesMap();
        return convertResultsToMap(results, limit, null);
    }

    @Cacheable("firstNamesMap")
    public Map<String, Integer> getFirstNamesMap(int min, int max, int limit) {
        List<Object[]> results = salariesRepository.findFirstNamesMap();

        Predicate<Object[]> range = entity -> {
            int value = ((Number) entity[1]).intValue();
            return value >= min && value <= max;
        };

        return convertResultsToMap(results, limit, range);
    }

    private Map<String, Integer> convertResultsToMap(List<Object[]> results, int limit, Predicate<Object[]> filter) {
        return results.stream()
                .filter(filter)
                .limit(limit)
                .collect(Collectors.toMap(
                        entity -> (String) entity[0],
                        entity -> ((Number) entity[1]).intValue(),
                        Integer::sum
                ));
    }

    public List<Salaries> getSalariesByTotalPay(double min, double max, int limit) {
        if (numbersValidate(min, max)) {
            return salariesRepository.findByTotalPayBetween(min, max, createPageable(limit)).getContent();
        } else {
            return getSalaries(limit);
        }
    }

    public Salaries getSalariesById(int id) {
        return salariesRepository.findSalariesById(id).orElseThrow();
    }
/*
    public Flux<Salaries> getSalariesQBE(String jobTitle) {
        Salaries probe = new Salaries();
        probe.setJobTitle(jobTitle);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths(
                        "id",
                        "basePay",
                        "overtimePay",
                        "otherPay",
                        "benefits",
                        "totalPay",
                        "totalPayBenefits",
                        "year",
                        "notes",
                        "agency",
                        "status"
                        )
                .withIgnoreCase("jobTitle")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Salaries> example = Example.of(probe, matcher);

        return salariesRepository.findAll(example);

    }
 */

    public List<Salaries> getSalariesWithStatus(int limit) {
        return salariesRepository.findByStatusIsNotNullAndStatusNot("", createPageable(limit)).getContent();
    }

    public boolean numbersValidate(int num1, int num2) {
        return num1 > 0 && num2 > 0 && num1 < num2;
    }

    public boolean numbersValidate(double num1, double num2) {
        return num1 > 0 && num2 > 0 && num1 < num2;
    }

    private Pageable createPageable(int limit) {
        return PageRequest.of(0, limit);
    }

    private Pageable createPageable(int limit, String sortBy) {
        return PageRequest.of(0, limit, Sort.by(sortBy).descending());
    }

    private Pageable createPageable(int limit, String sortBy, String thenSortBy) {
        return PageRequest.of(0, limit, Sort.by(sortBy).and(Sort.by(thenSortBy)));
    }

    private String getFirstName(String fullName) {
        int index = fullName.indexOf(" ");
        return index == -1 ? fullName : fullName.substring(0, index);
    }

    public int getAverageTotalPay() {
        Optional<Double> avg = salariesRepository.findAverageTotalPay();
        return (int) Math.ceil(avg.orElseThrow());
    }

    public int getAverageBasePay() {
        Optional<Double> avg = salariesRepository.findAverageBasePay();
        return (int) Math.ceil(avg.orElseThrow());
    }

    public int getAverageOtherPay() {
        Optional<Double> avg = salariesRepository.findAverageOtherPay();
        return (int) Math.ceil(avg.orElseThrow());
    }

    public Map<String, Integer> getAverages() {
        Optional<AverageDTO> averages = salariesRepository.findAverages();
        return averages.map(avg -> Map.of(
                "total_pay", (int) Math.ceil(avg.getTotal_pay()),
                "base_pay", (int) Math.ceil(avg.getBase_pay()),
                "other_pay", (int) Math.ceil(avg.getOther_pay())
        )).orElseThrow();
    }
    public List<String> getNames(int limit) {
        return salariesRepository.getSalariesByEmployeeName(createPageable(limit)).getContent();
    }
}
