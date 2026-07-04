package com.vatManager.vatManager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class PagedResourceAssemblerService<T> {

	private final PagedResourcesAssembler<T> pagedResourceAssembler;
	
	public PagedResourceAssemblerService(PagedResourcesAssembler<T> pagedResourceAssembler) {
		this.pagedResourceAssembler = pagedResourceAssembler;
	}

	
	
	
	
	public  PagedModel<EntityModel<T>> toPagedModel(Page<T> paginatedEntity){
		return (PagedModel<EntityModel<T>>) pagedResourceAssembler.toModel(paginatedEntity);
	}
	

	
	
	
}//ends class
