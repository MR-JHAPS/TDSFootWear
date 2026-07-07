package com.vatManager.vatManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vatManager.vatManager.apiResponse.ApiResponseBuilder;
import com.vatManager.vatManager.apiResponse.ApiResponseModel;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.WrapperClientResponse;
import com.vatManager.vatManager.service.ClientService;
import com.vatManager.vatManager.service.PagedResourceAssemblerService;
import com.vatManager.vatManager.utils.PageableUtils;


@RestController
@RequestMapping("/api/client")
public class ClientMonthlyController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ApiResponseBuilder responseBuilder;
	
	@Autowired
	private PagedResourceAssemblerService<WrapperClientResponse> pagedResourceAssembler;

	
	
	
	
	@GetMapping("/monthly")
	public ResponseEntity<ApiResponseModel<PagedModel<EntityModel<WrapperClientResponse>>>> getAllClientsMonthly(
						@RequestParam(defaultValue = "2") int size,
						@RequestParam(defaultValue = "0") int page, 
						@RequestParam(required = false) String sortBy,
						@RequestParam(required = false) String direction)
	{
		Pageable pageable = PageableUtils.createPageable(page, size, direction, sortBy);
		System.out.println("Initiating getAllClients from clientController");
		Page<WrapperClientResponse> clientResponseList = this.clientService.getAllClientsMonthly(pageable);
		PagedModel<EntityModel<WrapperClientResponse>> pagedModel = pagedResourceAssembler.toPagedModel(clientResponseList);
//		System.out.println(" THis is the API Sending DATA in get All Clients , " + clientResponseList);
//		for(WrapperClientResponse wrapper : clientResponseList) {
//			System.out.println(wrapper);
//			List<ClientResponseDto> responseList = wrapper.getClient();
//			for(ClientResponseDto client : responseList) {
//				System.out.println(client.getFoomName() + "this is the name of the FOOM");
//			}
//			
//			
//		}
		return responseBuilder.buildApiResponse("Client Obtained Successfully", pagedModel, HttpStatus.OK);
	}

}
