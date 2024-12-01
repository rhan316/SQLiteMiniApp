package com.example.sfsalaries.Gson;

import com.example.sfsalaries.data.SimpleSalaries;
import com.google.gson.GsonBuilder;
import pl.allegro.finance.tradukisto.ValueConverters;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalariesToJSON {
    public static void main(String[] args) {
        var url = "jdbc:sqlite:C:\\Users\\dar31\\OneDrive\\Pulpit\\sql\\sfSalary\\database.sqlite";
        var sql = "SELECT id, employee_name, job_title, total_pay, year FROM Salaries";
        List<SimpleSalaries> simpleSalaries = new LinkedList<>();

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)
                ) {

             while (rs.next()) {
                 simpleSalaries.add(new SimpleSalaries(
                         rs.getLong("id"),
                         rs.getString("employee_name"),
                         rs.getString("job_title"),
                         rs.getBigDecimal("total_pay"),
                         rs.getInt("year")
                 ));
             }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(simpleSalaries.size());

        Map<Integer, BigDecimal> totalPayByYear = simpleSalaries.stream()
                .collect(Collectors.groupingBy(
                    SimpleSalaries::getYear,
                    Collectors.reducing(
                            BigDecimal.ZERO,
                            SimpleSalaries::getTotalPay,
                            BigDecimal::add
                    )
                ));

        ValueConverters bigDecimalConv = ValueConverters.POLISH_INTEGER;
        System.out.println(bigDecimalConv.asWords(1234) + " złote");

    }
}
