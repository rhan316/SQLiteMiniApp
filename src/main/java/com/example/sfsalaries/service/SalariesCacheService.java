package com.example.sfsalaries.service;

import com.example.sfsalaries.data.Salaries;
import com.example.sfsalaries.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SalariesCacheService {

	private final SalariesRepository salariesRepository;
	private final ConcurrentHashMap<Long, List<Salaries>> cache;

	@Autowired
	public SalariesCacheService(SalariesRepository salariesRepository) {
		this.salariesRepository = salariesRepository;
		cache = new ConcurrentHashMap<>();
	}

	public Flux<Salaries> getAllSalaries() {
		return cache.containsKey(1L) ? Flux.fromIterable(cache.get(1L)) :
				salariesRepository.findAll()
						.collectList()
						.doOnNext(salaries -> cache.put(1L, salaries))
						.flatMapMany(Flux::fromIterable);
	}
}
