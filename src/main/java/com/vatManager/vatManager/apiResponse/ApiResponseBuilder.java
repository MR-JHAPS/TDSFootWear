package com.vatManager.vatManager.apiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseBuilder {

	public <T> ResponseEntity<ApiResponseModel<T>> buildApiResponse(String responseMessage, HttpStatus status ) {
		ApiResponseModel<T> responseModel = new ApiResponseModel<>(responseMessage);
		return ResponseEntity.status(status).body(responseModel);
	}
	
	
	public <T> ResponseEntity<ApiResponseModel<T>> buildApiResponse(String responseMessage, T body, HttpStatus status){
		ApiResponseModel<T> responseModel = new ApiResponseModel<>(responseMessage, body);
		return ResponseEntity.status(status).body(responseModel);
	}
	
	
	
}
