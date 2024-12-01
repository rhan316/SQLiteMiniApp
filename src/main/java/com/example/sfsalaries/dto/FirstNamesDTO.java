package com.example.sfsalaries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstNamesDTO {

	private String name;
	private Integer count;

	public FirstNamesDTO(String name, Long count) {
		this.name = name;
		this.count = count.intValue();
	}
}
