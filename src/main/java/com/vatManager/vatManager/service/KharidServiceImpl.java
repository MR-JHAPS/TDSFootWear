package com.vatManager.vatManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.KharidRequestDto;
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.entity.Client;
import com.vatManager.vatManager.entity.Kharid;
import com.vatManager.vatManager.exception.ClientNotFoundException;
import com.vatManager.vatManager.exception.KharidNotFoundException;
import com.vatManager.vatManager.repository.KharidRepository;
import com.vatManager.vatManager.utils.KharidMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class KharidServiceImpl implements KharidService{

	@Autowired
	private KharidRepository kharidRepo;
	
	@Autowired
	private KharidMapper kharidMapper;
	
	
	
	@Override
	public List<KharidResponseDto> getAllKharid() {
		log.info("Getting all the Kharids from the Database.");
		List<Kharid> kharidList = kharidRepo.findAll();
		List<KharidResponseDto> kharidResponseList = kharidList.stream().map(kharidMapper::toKharidResponseDto).collect(Collectors.toList());
		return kharidResponseList;
	}

	@Override
	public Page<KharidResponseDto> getAllKharid(Pageable pageable) {
		log.info("Getting all the Kharids from the Database.");
		Page<Kharid> kharidList = this.kharidRepo.findAll(pageable);
		Page<KharidResponseDto> pagedResponse  = kharidList.map(kharidMapper::toKharidResponseDto);
		return pagedResponse;
	}

	@Override
	public void deleteKharidById(int id) {
		log.info("Initiating delete Of Kharid of ID : {}", id);
		Kharid kharid = this.kharidRepo.findById(id)
					.orElseThrow(()-> new KharidNotFoundException("Kharid with id " + id + " not found") );
		kharidRepo.delete(kharid);
		
	}

	@Override
	public Kharid getKharidById(int id) {
		Kharid kharid = this.kharidRepo.findById(id)
				.orElseThrow(()-> new KharidNotFoundException("Kharid with id " + id + " not found"));
		return kharid;
	}

	@Override
	public KharidResponseDto getKharidDtoResponseById(int id) {
		Kharid kharid = this.getKharidById(id);
		return kharidMapper.toKharidResponseDto(kharid);
	}

	@Override
	public void updateKharidById(int id, KharidRequestDto request) {
		log.info("Initiating Updating Kharid By ID: {} " , id);
		Kharid kharid = getKharidById(id);
		//		Kharid updatedKharid = kharidMapper.toKharid(request);
		kharid.setFoomName(request.getFoomName());
		kharid.setBillNumber(request.getBillNumber());
		kharid.setAmount(request.getAmount());
		kharid.setYearInBs(request.getYearInBs());
		kharid.setMonthInBs(request.getMonthInBs());
		kharid.setDayInBs(request.getDayInBs());
		kharid.setPanNumber(request.getPanNumber());
		kharid.setVatTax(request.getVatTax());
		kharid.setTotal(request.getTotal());

		kharidRepo.save(kharid);
		log.info("Kharid {} Updated successfully. ", request.getFoomName());
			
		/*
		 * THIS IS ADVANCED THAT WILL NOT UPDATE THE DATA IF THE INPUT FIELD IS SET EMPTY.
		 * */
		
	}

	@Override
	public void insertKharid(KharidRequestDto request) {
		Kharid kharid = kharidMapper.toKharid(request);
		kharidRepo.save(kharid);
		
	}

	@Override
	public Page<KharidResponseDto> searchKharid(String searchQuery, Pageable pageable) {
		Page<Kharid> kharidList = kharidRepo.searchByQuery(searchQuery, pageable);
		Page<KharidResponseDto> kharidResponseList = kharidList.map(kharidMapper::toKharidResponseDto);
		return kharidResponseList;
	}

}
