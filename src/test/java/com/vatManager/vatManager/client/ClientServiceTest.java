package com.vatManager.vatManager.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.entity.Client;
import com.vatManager.vatManager.repository.ClientRepository;
import com.vatManager.vatManager.service.ClientService;
import com.vatManager.vatManager.service.ClientServiceImpl;
import com.vatManager.vatManager.utils.ClientMapper;
import com.vatManager.vatManager.utils.PageableUtils;

public class ClientServiceTest {

	@Mock
	ClientRepository clientRepo;
	
	@InjectMocks
	ClientServiceImpl clientService;
	
	@Spy
	ClientMapper clientMapper;
	
	
	public ClientServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void testGetAllClients() {
		List<Client> clientList = new ArrayList<>(Arrays.asList(
										new Client(1, "tds", LocalDate.now(), 1111, 3333, 12000, 2000, 14000),
										new Client(2, "sagarmatha", LocalDate.now(), 1231, 2321, 10000, 1000, 11000) 
										) );
		
		Page<Client> pagedClientList = new PageImpl<Client>(clientList);

		
		Page<ClientResponseDto> pagedClients = pagedClientList.map(clientMapper::toClientResponseDto);
		// mocking Repository/database	
		when(clientRepo.findAll()).thenReturn(clientList);
		
		//mocking ClientMapper.
		when(clientMapper.toClientResponseDto(clientList.get(0))).thenReturn(new ClientResponseDto(1, "tds", LocalDate.now(), 1111, 3333, 12000, 2000, 14000));
		when(clientMapper.toClientResponseDto(clientList.get(1))).thenReturn(new ClientResponseDto(2, "sagarmatha", LocalDate.now(), 1231, 2321, 10000, 1000, 11000));
		
		//Pageable mocking Data:
		Pageable pageable = PageRequest.of(0, 10);
		
		//Calling service		
		Page<ClientResponseDto> response = clientService.getAllClients(pageable);
		
		//Assertions: 
		assertEquals(2, response.getNumberOfElements());
		assertEquals("tds", response.getContent().get(0).getFoomName());
		assertEquals("sagarmatha", response.getContent().get(1).getFoomName());
		
		//verifying:
		verify(clientRepo, times(1)).findAll();
		verify(clientMapper, times(1)).toClientResponseDto(clientList.get(0));
		verify(clientMapper, times(1)).toClientResponseDto(clientList.get(1));
		
	}//ends method
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//ends test Class
