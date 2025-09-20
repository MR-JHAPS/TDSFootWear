package com.vatManager.vatManager.utils;

import org.springframework.stereotype.Component;

import com.vatManager.vatManager.dto.ClientRequestDto;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.entity.Client;

@Component
public class ClientMapper {
	
	
	
	public Client toClient(ClientRequestDto dto) {
		Client client = Client.builder()
				.foomName(dto.getFoomName())
				.billNumber(dto.getBillNumber())
				.amount(dto.getAmount())
				.panNumber(dto.getPanNumber())
				.total(dto.getTotal())
				.vatTax(dto.getVatTax())
				.date(dto.getDate())
				.build();
		return client;
	}
	
	
	
	
	public ClientResponseDto toClientResponseDto(Client client) {
		ClientResponseDto responseDto = ClientResponseDto.builder()
				.id(client.getId())
				.foomName(client.getFoomName())
				.billNumber(client.getBillNumber())
				.amount(client.getAmount())
				.panNumber(client.getPanNumber())
				.total(client.getTotal())
				.vatTax(client.getVatTax())
				.date(client.getDate())
				.build();
		return responseDto;
	}
	
	
	
	

}
