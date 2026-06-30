package com.vatManager.vatManager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.vatManager.vatManager.apiResponse.ApiResponseBuilder;
import com.vatManager.vatManager.apiResponse.ApiResponseModel;
import com.vatManager.vatManager.dto.KharidRequestDto;
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.service.KharidService;
import com.vatManager.vatManager.service.PagedResourceAssemblerService;
import com.vatManager.vatManager.utils.ExcelUtils;
import com.vatManager.vatManager.utils.PageableUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kharid")
public class KharidController {

	@Autowired
	private ApiResponseBuilder responseBuilder;
	
	@Autowired
	private PagedResourceAssemblerService<KharidResponseDto> pagedResourceAssembler;
	
	@Autowired
	private KharidService kharidService;

	
	
	

	@GetMapping
	public ResponseEntity<ApiResponseModel<PagedModel<EntityModel<KharidResponseDto>>>> getAllKharids(
						@RequestParam(defaultValue = "10") int size,
						@RequestParam(defaultValue = "0") int page, 
						@RequestParam(required = false) String sortBy,
						@RequestParam(required = false) String direction)
	{
		Pageable pageable = PageableUtils.createPageable(page, size, direction, sortBy);
		Page<KharidResponseDto> kharidResponseList = this.kharidService.getAllKharid(pageable);
		PagedModel<EntityModel<KharidResponseDto>> pagedModel = pagedResourceAssembler.toPagedModel(kharidResponseList);
		return responseBuilder.buildApiResponse("Kharids Obtained Successfully", pagedModel, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseModel<KharidResponseDto>> getKharidById(@PathVariable int id){
		KharidResponseDto kharidResponse = kharidService.getKharidDtoResponseById(id);
		return responseBuilder.buildApiResponse("Kharid Obtained Successfully", kharidResponse, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/insert")
	public ResponseEntity<ApiResponseModel<String>> insertNewKharid(@RequestBody @Valid KharidRequestDto request) {
		this.kharidService.insertKharid(request);
		return responseBuilder.buildApiResponse("Kharid Successfully Inserted", HttpStatus.OK);
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseModel<String>> updateKharid(@PathVariable int id, @RequestBody KharidRequestDto request) {
		this.kharidService.updateKharidById(id, request);
		return responseBuilder.buildApiResponse("Kharid Successfully Updated", HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseModel<String>> deleteKharidById(@PathVariable int id) {
		this.kharidService.deleteKharidById(id);
		return responseBuilder.buildApiResponse("Kharid Successfully Deleted", HttpStatus.NO_CONTENT);
	}
	
	
	
	
	// Theoretically this should work.
	@GetMapping("/download/kharids")
	public ResponseEntity<byte[]> exportKharidsToExcel() {
		List<KharidResponseDto> kharidList = kharidService.getAllKharid();
		System.out.println("Downloading Excel File of Kharids");
		try {
			byte[] byteValue = ExcelUtils.createExcelBytesKharid(kharidList);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "tdsClientsKharid.xlsx");
//			ApiResponseModel<byte[]> customResponse = new ApiResponseModel<byte[]>("Excel Data for Client sent successfully.", byteValue);
			System.out.println("success with excel file of Kharid");
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(byteValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponseModel<PagedModel<EntityModel<KharidResponseDto>>>> searchKharidsByQuery(
			@RequestParam String query,
			@RequestParam (defaultValue = "0") int page,
			@RequestParam (defaultValue = "10") int size,
			@RequestParam (required = false) String direction,
			@RequestParam (required = false) String sortBy
			){
		System.out.println("Search Controller called");
		Pageable pageable = PageableUtils.createPageable(page, size, direction, sortBy);
		Page<KharidResponseDto> kharidResponseList = kharidService.searchKharid(query, pageable);
		PagedModel<EntityModel<KharidResponseDto>> pagedModel = pagedResourceAssembler.toPagedModel(kharidResponseList);
		return responseBuilder.buildApiResponse("Kharid Search Successfully obtained", pagedModel, HttpStatus.OK);
	}
	
	
	
}
