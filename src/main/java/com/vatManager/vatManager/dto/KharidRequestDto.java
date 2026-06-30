package com.vatManager.vatManager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KharidRequestDto {

	@NotBlank(message = "foomName cannot be blank in KharidRequestDto")
	private String foomName;

	@NotNull(message = "date cannot be null in KharidRequestDto")
	private LocalDate date;

	@NotNull(message = "Bill Number cannot be blank in KharidRequestDto")
	private Integer billNumber;
		
	@NotNull(message = "Pan Number cannot be blank in KharidRequestDto")
	private Integer panNumber;
	
	@NotNull(message = "Amount cannot be blank in KharidRequestDto")
	private BigDecimal amount;
	
	@NotNull(message = "VatTax cannot be blank in KharidRequestDto")
	private BigDecimal vatTax;
	
	@NotNull(message = "Total cannot be blank in KharidRequestDto")
	private BigDecimal total;
		
	
}
