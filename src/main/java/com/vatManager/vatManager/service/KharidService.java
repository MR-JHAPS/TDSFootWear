package com.vatManager.vatManager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.KharidRequestDto;
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.entity.Kharid;

public interface KharidService {

	List<KharidResponseDto> getAllKharid();
	
	Page<KharidResponseDto> getAllKharid(Pageable pageable);
	
	void deleteKharidById(int id);
	
	Kharid getKharidById(int id);
	
	KharidResponseDto getKharidDtoResponseById(int id);
	
	void updateKharidById(int id, KharidRequestDto request);
	
	void insertKharid(KharidRequestDto request);
	
	Page<KharidResponseDto> searchKharid(String searchQuery, Pageable pageable);
	
}
