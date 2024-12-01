package com.example.sfsalaries.repository;

import com.example.sfsalaries.data.Salaries;
import com.example.sfsalaries.dto.AverageDTO;
import com.example.sfsalaries.dto.AverageYearDTO;
import com.example.sfsalaries.dto.FirstNamesDTO;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface SalariesRepository extends JpaRepository<Salaries, Long> {

    Optional<Salaries> findSalariesById(int id);

    Page<Salaries> findByYearBetween(int minYear, int maxYear, Pageable pageable);
    Page<Salaries> findByTotalPayBetween(double totalPay, double totalPay2, Pageable pageable);
    Page<Salaries> findByJobTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Salaries> findByStatusIsNotNullAndStatusNot(String emptyString, Pageable pageable);

    @NonNull
    Page<Salaries> findAll(@NonNull Pageable pageable);

    @Query("SELECT s.employeeName FROM Salaries s")
    Page<String> getSalariesByEmployeeName(Pageable pageable);

    @Query("SELECT AVG(s.totalPay) FROM Salaries s")
    Optional<Double> findAverageTotalPay();

    @Query("SELECT AVG(s.basePay) FROM Salaries s")
    Optional<Double> findAverageBasePay();

    @Query("SELECT AVG(s.otherPay) FROM Salaries s")
    Optional<Double> findAverageOtherPay();

    @Query("SELECT new com.example.sfsalaries.dto.AverageDTO(AVG(s.totalPay), AVG(s.basePay), AVG(s.otherPay)) FROM Salaries s")
    Optional<AverageDTO> findAverages();

    @Query("SELECT new com.example.sfsalaries.dto.AverageYearDTO(s.year, AVG(s.totalPay)) FROM Salaries s GROUP BY s.year")
    List<AverageYearDTO> findAvgByYear();

    @Query("SELECT s.employeeName FROM Salaries s ORDER BY s.employeeName ASC")
    List<String> findEmployeeFullNames();

    @Query(value = """
            SELECT SUBSTR(s.employee_name, 1, INSTR(s.employee_name, ' ') - 1) AS first_name,
            COUNT(s.employee_name) AS count
            FROM Salaries s
            GROUP BY s.employee_name
            ORDER BY s.employee_name ASC
            """, nativeQuery = true)
    List<Object[]> findFirstNamesMap();
}
