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
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.dto.WrapperKharidResponse;
import com.vatManager.vatManager.service.KharidService;
import com.vatManager.vatManager.service.PagedResourceAssemblerService;
import com.vatManager.vatManager.utils.PageableUtils;

@RestController
@RequestMapping("/api/kharid")
public class KharidMonthlyController {
	
	@Autowired
	private ApiResponseBuilder responseBuilder;
	
	@Autowired
	private PagedResourceAssemblerService<WrapperKharidResponse> pagedResourceAssembler;
	
	@Autowired
	private KharidService kharidService;

	
	
	@GetMapping("/monthly")
	public ResponseEntity<ApiResponseModel<PagedModel<EntityModel<WrapperKharidResponse>>>> getAllKharidsMonthly(
						@RequestParam(defaultValue = "10") int size,
						@RequestParam(defaultValue = "0") int page, 
						@RequestParam(required = false) String sortBy,
						@RequestParam(required = false) String direction)
	{
		Pageable pageable = PageableUtils.createPageable(page, size, direction, sortBy);
		System.out.println("Initiating getAllClients from clientController");
		Page<WrapperKharidResponse> kharidResponseList = this.kharidService.getAllKharidMonthly(pageable);
		PagedModel<EntityModel<WrapperKharidResponse>> pagedModel = pagedResourceAssembler.toPagedModel(kharidResponseList);
//		System.out.println(" THis is the API Sending DATA in get All Clients , " + clientResponseList);
		for(WrapperKharidResponse wrapper : kharidResponseList) {
			System.out.println(wrapper);
			List<KharidResponseDto> responseList = wrapper.getClient();
			for(KharidResponseDto kharid : responseList) {
				System.out.println(kharid.getFoomName() + "this is the name of the FOOM");
			}
			
			
		}
		return responseBuilder.buildApiResponse("Kharid Obtained Successfully", pagedModel, HttpStatus.OK);
	}

	
	
	

}
