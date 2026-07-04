package com.vatManager.vatManager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vatManager.vatManager.dto.ClientRequestDto;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.WrapperClientResponse;
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
	public Page<WrapperClientResponse> getAllClientsMonthly(Pageable pageable) {
		log.info("Getting all the Clients from the Database.");
		Page<Client> clientList = this.clientRepo.findAllMonthly(pageable);
		Page<ClientResponseDto> pagedResponse  = clientList.map(clientMapper::toClientResponseDto);		
		Page<WrapperClientResponse> wrappedPagedResponse = getWrapperClientResponseList(pagedResponse);
		
		log.info("Request has Arrived at getAllClientsMonthly(Pageable pageable) in ClientServiceImpl.");
		return wrappedPagedResponse;
	}
	@Override
	public Page<ClientResponseDto> getAllClients(Pageable pageable) {
		log.info("Getting all the Clients from the Database.");
		Page<Client> clientList = this.clientRepo.findAll(pageable);
		log.info("Fetched from ClientRepository");
		Page<ClientResponseDto> pagedResponse  = clientList.map(clientMapper::toClientResponseDto);		
		
		log.info("Request has Arrived at getAllClients(Pageable pageable) in ClientServiceImpl.");
		return pagedResponse;
	}
	
	

	@Override
	public void deleteClientById(int id) {
		log.info("Initiating delete Of Client of ID : {}", id);
		Client client = this.clientRepo.findById(id)
					.orElseThrow(()-> new ClientNotFoundException("Client with id " + id + " not found") );
		clientRepo.delete(client);
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
		client.setYearInBs(request.getYearInBs());
		client.setMonthInBs(request.getMonthInBs());
		client.setDayInBs(request.getDayInBs());
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

	
	
	@Override
	public Page<ClientResponseDto> searchClient(String searchQuery, Pageable pageable) {
		Page<Client> clientList = clientRepo.searchByQuery(searchQuery, pageable);
		Page<ClientResponseDto> clientResponseList = clientList.map(clientMapper::toClientResponseDto);
		return clientResponseList;
	}
//	@Override
//	public Page<ClientResponseDto> searchClient(String searchQuery, Pageable pageable) {
//		Page<Client> clientList = clientRepo.searchByQuery(searchQuery, pageable);
//		Page<ClientResponseDto> clientResponseList = clientList.map(clientMapper::toClientResponseDto);
//		return clientResponseList;
//	}
	
	
	
//	public Page<WrapperClientResponse> getPagedClientResponse(Pageable pageable){
//		
//		Page<ClientResponseDto> rawClientList = getAllClients(pageable);
//		List<WrapperClientResponse> = getWrapperClientResponseList(getAllClients());
//	}
	
	
	
	
//	public List<WrapperClientResponse> getWrapperClientResponseList(List<ClientResponseDto> rawClientList){
//		List<WrapperClientResponse> wrappedResponseList = new ArrayList<WrapperClientResponse>();
//		int dbLength = rawClientList.size();
//		
//		for(int i=0; i<dbLength ; i++) {
//			WrapperClientResponse currentMonthClientsWrapper = null;
//			List<ClientResponseDto> clientOfGivenMonth = null;
//			BigDecimal monthlyTotal = null;
//			
//			//ensuring the rawClientList obtained from the Database is not null and valid.
//			if(rawClientList!=null && rawClientList.size()!=0) {
//				
//				//if the year && month of row(i) and row(i+1) are same update monthlyTotal and ClientList
//				if(
//					rawClientList.get(i).getYearInBs().equals(rawClientList.get(i+1).getYearInBs()) &&
//					rawClientList.get(i).getMonthInBs().equals(rawClientList.get(i+1).getMonthInBs())	
//					) {
//					monthlyTotal = rawClientList.get(i).getTotal().add( monthlyTotal);
//					clientOfGivenMonth.add(rawClientList.get(i));
//					
//				}//ends Inner-if
//				
//				// If the condition of i & i+1 are not equal then push the achieved data to the wrapperClientResponseList.
//				else {
//					currentMonthClientsWrapper.setClient(clientOfGivenMonth);
//					currentMonthClientsWrapper.setMonthlyTotal(monthlyTotal);
//					wrappedResponseList.add(currentMonthClientsWrapper);
//					currentMonthClientsWrapper = new WrapperClientResponse(null, null);
//					clientOfGivenMonth = null;
//					monthlyTotal = null;
//					return wrappedResponseList;
//					
//				}
//				
//			}//Ends Outer-If
//			
//			
//			
//		}//ends For-Loop
//		
//		
//		return wrappedResponseList;
////		return new ArrayList<WrapperClientResponse>();
//	}//ends Method
	
	
	
	public Page<WrapperClientResponse> getWrapperClientResponseList(Page<ClientResponseDto> rawClientList){
		
		List<ClientResponseDto> content = rawClientList.getContent();
		
		for(ClientResponseDto client : content) {
			System.out.println(client.getFoomName() + " Inside the wrapperServiceHandler");
		}
		
		
		
		List<WrapperClientResponse> wrappedResponseList = new ArrayList<>();
		
		//ensuring the rawClientList obtained from the Database is not null and valid.
		if(content==null || content.isEmpty()) {
			log.info("Inside Wrapper method the Content received is empty");
			return new PageImpl<>(wrappedResponseList, rawClientList.getPageable(), 0);
		}
		
		List<ClientResponseDto> clientOfGivenMonth = new ArrayList<>();
		BigDecimal monthlyTotal = BigDecimal.ZERO;
		
		
		for(int i=0; i<content.size() ; i++) {
			ClientResponseDto current = content.get(i);
			clientOfGivenMonth.add(current);
			monthlyTotal = monthlyTotal.add(current.getTotal());
			
			
			
			//content.size()-1 because "i" starts from zero so if 20 clients starting from 0 results to 19 count. Hence "-1".
			boolean isLast = (i==content.size()-1);
			
			//if the year && month of row(i) and row(i+1) are same
			boolean nextIsDifferentMonth = !isLast &&
	                (!current.getYearInBs().equals(content.get(i + 1).getYearInBs()) ||
                    !current.getMonthInBs().equals(content.get(i + 1).getMonthInBs()));
				
				if(isLast || nextIsDifferentMonth) {
					 wrappedResponseList.add(new WrapperClientResponse(clientOfGivenMonth, monthlyTotal));

			            clientOfGivenMonth = new ArrayList<>();
			            monthlyTotal = BigDecimal.ZERO;
				}
				
				
		}//ends For-Loop
				
				// If the condition of i & i+1 are not equal then push the achieved data to the wrapperClientResponseList.
			    return new PageImpl<>(wrappedResponseList, rawClientList.getPageable(), wrappedResponseList.size());
				
	}//ends Method
	
	
	
	
	
	
	
}
