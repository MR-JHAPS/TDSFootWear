package com.vatManager.vatManager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class KharidResponseDto {

	private Integer id;

	private String foomName;

	private LocalDate date;

	private Integer billNumber;
	
	private Integer panNumber;
	
	private BigDecimal amount;
	
	private BigDecimal vatTax;
	
	private BigDecimal total;
}
