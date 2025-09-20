package com.vatManager.vatManager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.vatManager.vatManager.apiResponse.ApiResponseBuilder;
import com.vatManager.vatManager.apiResponse.ApiResponseModel;
import com.vatManager.vatManager.dto.ClientRequestDto;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.service.ClientService;
import com.vatManager.vatManager.service.PagedResourceAssemblerService;
import com.vatManager.vatManager.utils.ExcelUtils;
import com.vatManager.vatManager.utils.PageableUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ApiResponseBuilder responseBuilder;
	
	@Autowired
	private PagedResourceAssemblerService<ClientResponseDto> pagedResourceAssembler;

	

	@GetMapping
	public ResponseEntity<ApiResponseModel<PagedModel<EntityModel<ClientResponseDto>>>> getAllClients(
						@RequestParam(defaultValue = "10") int size,
						@RequestParam(defaultValue = "0") int page, 
						@RequestParam(required = false) String sortBy,
						@RequestParam(required = false) String direction)
	{
		Pageable pageable = PageableUtils.createPageable(page, size, direction, sortBy);
		Page<ClientResponseDto> clientResponseList = this.clientService.getAllClients(pageable);
		PagedModel<EntityModel<ClientResponseDto>> pagedModel = pagedResourceAssembler.toPagedModel(clientResponseList);
		return responseBuilder.buildApiResponse("Client Obtained Successfully", pagedModel, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseModel<ClientResponseDto>> getClientById(@PathVariable int id){
		ClientResponseDto clientResponse = clientService.getClientDtoResponseById(id);
		return responseBuilder.buildApiResponse("Client Obtained Successfully", clientResponse, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/insert")
	public ResponseEntity<ApiResponseModel<String>> insertNewClient(@RequestBody @Valid ClientRequestDto request) {
		this.clientService.insertClient(request);
		return responseBuilder.buildApiResponse("Client Successfully Inserted", HttpStatus.OK);
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseModel<String>> updateClient(@PathVariable int id, @RequestBody ClientRequestDto request) {
		this.clientService.updateClientById(id, request);
		return responseBuilder.buildApiResponse("Client Successfully Updated", HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseModel<String>> deleteClientById(@PathVariable int id) {
		this.clientService.deleteClientById(id);
		return responseBuilder.buildApiResponse("Client Successfully Deleted", HttpStatus.NO_CONTENT);
	}
	
	
	
	
	// Theoretically this should work.
	@GetMapping("/download/clients")
	public ResponseEntity<ApiResponseModel<byte[]>> exportClientsToExcel() {
		List<ClientResponseDto> clientList = clientService.getAllClients();
		try {
			byte[] byteValue = ExcelUtils.createExcelBytes(clientList);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "tdsClients.xlsx");
			ApiResponseModel<byte[]> customResponse = new ApiResponseModel<byte[]>("Excel Data for Client sent successfully.", byteValue);
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(customResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApiResponseModel<byte[]> errorResponse = new ApiResponseModel<>("Error sending the Excel download file.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
	}
	
	
	
}
