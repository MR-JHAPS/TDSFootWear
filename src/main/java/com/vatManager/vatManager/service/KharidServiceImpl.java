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

import com.vatManager.vatManager.dto.KharidRequestDto;
import com.vatManager.vatManager.dto.KharidResponseDto;
import com.vatManager.vatManager.dto.WrapperKharidResponse;
import com.vatManager.vatManager.entity.Kharid;
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
	public Page<WrapperKharidResponse> getAllKharidMonthly(Pageable pageable) {
		log.info("Getting all the Kharids from the Database.");
		Page<Kharid> kharidList = this.kharidRepo.findAllMonthly(pageable);
		Page<KharidResponseDto> pagedResponse  = kharidList.map(kharidMapper::toKharidResponseDto);		
		Page<WrapperKharidResponse> wrappedPagedResponse = getWrapperKharidResponseList(pagedResponse);
		
		log.info("Request has Arrived at getAllKharidMonthly(Pageable pageable) in KharidServiceImpl.");
		return wrappedPagedResponse;
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
	
	
	
public Page<WrapperKharidResponse> getWrapperKharidResponseList(Page<KharidResponseDto> rawKharidList){
		
		List<KharidResponseDto> content = rawKharidList.getContent();
		
		for(KharidResponseDto kharid : content) {
			System.out.println(kharid.getFoomName() + " Inside the wrapperServiceHandler of Kharid");
		}
		
		List<WrapperKharidResponse> wrappedResponseList = new ArrayList<>();
		
		//ensuring the rawClientList obtained from the Database is not null and valid.
		if(content==null || content.isEmpty()) {
			log.info("Inside Wrapper method the Content received is empty kharidServiceImpl");
			return new PageImpl<>(wrappedResponseList, rawKharidList.getPageable(), 0);
		}
		
		List<KharidResponseDto> kharidOfGivenMonth = new ArrayList<>();
		BigDecimal monthlyTotal = BigDecimal.ZERO;
		
		for(int i=0; i<content.size() ; i++) {
			KharidResponseDto current = content.get(i);
			kharidOfGivenMonth.add(current);
			monthlyTotal = monthlyTotal.add(current.getTotal());
			
			//content.size()-1 because "i" starts from zero so if 20 clients starting from 0 results to 19 count. Hence "-1".
			boolean isLast = (i==content.size()-1);
			
			//if the year && month of row(i) and row(i+1) are same
			boolean nextIsDifferentMonth = !isLast &&
	                (!current.getYearInBs().equals(content.get(i + 1).getYearInBs()) ||
                    !current.getMonthInBs().equals(content.get(i + 1).getMonthInBs()));
				
				if(isLast || nextIsDifferentMonth) {
					 wrappedResponseList.add(new WrapperKharidResponse(kharidOfGivenMonth, monthlyTotal));

			            kharidOfGivenMonth = new ArrayList<>();
			            monthlyTotal = BigDecimal.ZERO;
				}//ends if-loop.	
		}//ends For-Loop
				// If the condition of i & i+1 are not equal then push the achieved data to the wrapperClientResponseList.
			    return new PageImpl<>(wrappedResponseList, rawKharidList.getPageable(), wrappedResponseList.size());				
	}//ends Method




	
	
	

}//ends class
