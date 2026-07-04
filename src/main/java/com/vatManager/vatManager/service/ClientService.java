package com.vatManager.vatManager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vatManager.vatManager.dto.ClientRequestDto;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.WrapperClientResponse;
import com.vatManager.vatManager.entity.Client;

public interface ClientService {

	
	List<ClientResponseDto> getAllClients();
	
	Page<ClientResponseDto> getAllClients(Pageable pageable);
	
	Page<WrapperClientResponse> getAllClientsMonthly(Pageable pageable);
	
	Page<WrapperClientResponse> getWrapperClientResponseList(Page<ClientResponseDto> rawClientList);	
	
	void deleteClientById(int id);
	
	Client getClientById(int id);
	
	ClientResponseDto getClientDtoResponseById(int id);
	
	void updateClientById(int id, ClientRequestDto request);
	
	void insertClient(ClientRequestDto request);
	
//	Page<ClientResponseDto> searchClient(String searchQuery, Pageable pageable);
	Page<ClientResponseDto> searchClient(String searchQuery, Pageable pageable);
	
}
