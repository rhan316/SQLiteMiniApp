package com.example.sfsalaries.shell;

import com.example.sfsalaries.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RepositoryCommands {

	private final SalariesRepository salariesRepository;
	private final Environment environment;

	@Autowired
	public RepositoryCommands(SalariesRepository salariesRepository, Environment environment) {
		this.salariesRepository = salariesRepository;
		this.environment = environment;
	}

	@ShellMethod("Display employees name and job title.")
	public void nameAndJobTitle(int limit) {
		salariesRepository.findAll(PageRequest.of(0, limit))
				.forEach(e -> System.out.println(e.getEmployeeName() + " => " + e.getJobTitle()));
	}

	@ShellMethod("Display average total pay.")
	public double avgTotalPay() {
		return salariesRepository.findAverageTotalPay().orElseThrow();
	}
}
