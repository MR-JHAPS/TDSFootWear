package com.vatManager.vatManager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {

	@NotBlank(message = "foomName cannot be blank in ClientRequestDto")
	private String foomName;


	@NotNull(message = "yearInBs cannot be null in ClientRequestDto")
	private Integer yearInBs;
	
	@NotNull(message = "monthInBs cannot be null in ClientRequestDto")
	private Integer monthInBs;
	
	@NotNull(message = "dayInBs cannot be null in ClientRequestDto")
	private Integer dayInBs;
	
	@NotNull(message = "Bill Number cannot be blank in ClientRequestDto")
	private Integer billNumber;
		
	@NotNull(message = "Pan Number cannot be blank in ClientRequestDto")
	private Integer panNumber;
	
	@NotNull(message = "Amount cannot be blank in ClientRequestDto")
	private BigDecimal amount;
	
	@NotNull(message = "VatTax cannot be blank in ClientRequestDto")
	private BigDecimal vatTax;
	
	@NotNull(message = "Total cannot be blank in ClientRequestDto")
	private BigDecimal total;
	
	
	
}