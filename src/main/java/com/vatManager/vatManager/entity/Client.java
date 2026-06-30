package com.vatManager.vatManager.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String foomName;
	
	private Integer yearInBs;
	
	private Integer monthInBs;
	
	private Integer dayInBs;

	private Integer billNumber;
	
	private Integer panNumber;
	
	private BigDecimal amount;
	
	private BigDecimal vatTax;
	
	private BigDecimal total;
	
	
	
}
