package com.vatManager.vatManager.utils;

import org.springframework.stereotype.Component;

import com.vatManager.vatManager.dto.KharidRequestDto;
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.entity.Kharid;

@Component
public class KharidMapper {

	public Kharid toKharid(KharidRequestDto dto) {
		Kharid kharid = Kharid.builder()
				.foomName(dto.getFoomName())
				.billNumber(dto.getBillNumber())
				.amount(dto.getAmount())
				.panNumber(dto.getPanNumber())
				.total(dto.getTotal())
				.vatTax(dto.getVatTax())
				.date(dto.getDate())
				.build();
		return kharid;
	}
	
	
	
	
	public KharidResponseDto toKharidResponseDto(Kharid kharid) {
		KharidResponseDto responseDto = KharidResponseDto.builder()
				.id(kharid.getId())
				.foomName(kharid.getFoomName())
				.billNumber(kharid.getBillNumber())
				.amount(kharid.getAmount())
				.panNumber(kharid.getPanNumber())
				.total(kharid.getTotal())
				.vatTax(kharid.getVatTax())
				.date(kharid.getDate())
				.build();
		return responseDto;
	}
	
	
}
