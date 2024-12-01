package com.example.sfsalaries;

import com.example.sfsalaries.data.Salaries;
import com.example.sfsalaries.repository.SalariesRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SfSalariesApplication implements CommandLineRunner {

	private final SalariesRepository salariesRepository;

	@Autowired
	public SfSalariesApplication(SalariesRepository salariesRepository) {
		this.salariesRepository = salariesRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SfSalariesApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		List<Salaries> salaries = salariesRepository.findAll();

		var totalPayStats = salaries.parallelStream()
				.mapToDouble(Salaries::getTotalPay)
				.summaryStatistics();

		var basePayStats = salaries.parallelStream()
				.mapToDouble(Salaries::getBasePay)
				.summaryStatistics();

		var otherPayStats = salaries.parallelStream()
				.mapToDouble(Salaries::getOtherPay)
				.summaryStatistics();

		Set<Integer> years = salaries.parallelStream()
						.mapToInt(Salaries::getYear)
						.distinct()
						.collect(
								HashSet::new,
								HashSet::add,
								HashSet::addAll
						);

		Set<String> status = salaries.parallelStream()
						.map(Salaries::getStatus)
							.distinct()
								.collect(
										HashSet::new,
										HashSet::add,
										HashSet::addAll
								);

		List<String> employeesNames = salaries.parallelStream()
						.map(Salaries::getEmployeeName)
							.distinct()
								.collect(
										ArrayList::new,
										ArrayList::add,
										ArrayList::addAll
								);

		years.forEach(e -> System.out.print(e + " "));
		System.out.println();
		status.forEach(e -> System.out.print(e + " "));
		System.out.println();

		employeesNames.stream()
				.limit(10)
				.forEach(e -> System.out.println(e + " "));
		System.out.println();


		var factory = new ProxyFactory(new ArrayList<>());
		MethodInterceptor methodInterceptor = invocation -> (invocation.getMethod().getName().equals("add") &&
				isNull(invocation.getArguments()[0])) ? false : invocation.proceed();
		factory.addAdvice(methodInterceptor);

		List<String> list = (List<String>) factory.getProxy();
		list.add("One");
		System.out.println(list);
		list.add(null);
		list.add("Two");
		System.out.println(list);
	}
}
