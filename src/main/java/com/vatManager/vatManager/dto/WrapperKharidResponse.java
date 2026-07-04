package com.vatManager.vatManager.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WrapperKharidResponse {
	
	private List<KharidResponseDto> client;
	private BigDecimal monthlyTotal;

}
