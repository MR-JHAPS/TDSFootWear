package com.vatManager.vatManager.apiResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class  ApiResponseModel<T> {
 
	private String message;
	
	private LocalDateTime timestamp;
	
	private T data;
	
	
	public ApiResponseModel( String message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.data = null;
	}
	
	public ApiResponseModel( String message, T data) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.data = data;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getTimestamp() {
		return this.timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	public T getData() {
		return this.data;
	}
	
	
	
}
