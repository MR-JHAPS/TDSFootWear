package com.vatManager.vatManager.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ClientResponseDto {
	
	private Integer id;

	private String foomName;

	private LocalDate date;

	private Integer billNumber;
	
	private Integer panNumber;
	
	private Integer amount;
	
	private Integer vatTax;
	
	private Integer total;
}
