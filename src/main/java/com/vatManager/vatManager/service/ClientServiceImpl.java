package com.vatManager.vatManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vatManager.vatManager.dto.ClientRequestDto;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.entity.Client;
import com.vatManager.vatManager.exception.ClientNotFoundException;
import com.vatManager.vatManager.repository.ClientRepository;
import com.vatManager.vatManager.utils.ClientMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ClientMapper clientMapper;

	//Get All Clients	
	@Override
	public List<ClientResponseDto> getAllClients() {
		log.info("Getting all the Clients from the Database.");
		List<Client> clientList = clientRepo.findAll();
		List<ClientResponseDto> clientResponseList = clientList.stream().map(clientMapper::toClientResponseDto).collect(Collectors.toList());
		return clientResponseList;
	}
	
	@Override
	public Page<ClientResponseDto> getAllClients(Pageable pageable) {
		log.info("Getting all the Clients from the Database.");
		Page<Client> clientList = this.clientRepo.findAll(pageable);
		Page<ClientResponseDto> pagedResponse  = clientList.map(clientMapper::toClientResponseDto);
		return pagedResponse;
	}
	
	

	@Override
	public void deleteClientById(int id) {
		log.info("Initiating delete Of Client of ID : {}", id);
		this.clientRepo.findById(id)
					.orElseThrow(()-> new ClientNotFoundException("Client with id " + id + " not found") );
		
	}
	
	@Override
	public Client getClientById(int id) {
		Client client = this.clientRepo.findById(id)
				.orElseThrow(()-> new ClientNotFoundException("Client with id " + id + " not found"));
		return client;
	}
	

	@Override
	public ClientResponseDto getClientDtoResponseById(int id) {
		Client client = this.getClientById(id);
		return clientMapper.toClientResponseDto(client);
	}

	
	
	@Override
	public void updateClientById(int id, ClientRequestDto request) {
		log.info("Initiating Updating Client By ID: {} " , id);
		Client client = getClientById(id);
		//		Client updatedClient = clientMapper.toClient(request);
		client.setFoomName(request.getFoomName());
		client.setBillNumber(request.getBillNumber());
		client.setAmount(request.getAmount());
		client.setDate(request.getDate());
		client.setPanNumber(request.getPanNumber());
		client.setVatTax(request.getVatTax());
		client.setTotal(request.getTotal());

		clientRepo.save(client);
		log.info("Client {} Updated successfully. ", request.getFoomName());
			
		/*
		 * THIS IS ADVANCED THAT WILL NOT UPDATE THE DATA IF THE INPUT FIELD IS SET EMPTY.
		 * */
	}

	
	
	@Override
	public void insertClient(ClientRequestDto request) {
		Client client = clientMapper.toClient(request);
		clientRepo.save(client);
	}
	
	
	
	
	
	
	
	
	
	
	
}
