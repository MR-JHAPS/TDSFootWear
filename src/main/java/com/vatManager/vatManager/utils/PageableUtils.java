package com.vatManager.vatManager.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageableUtils {

	
	
	public static Pageable createPageable(int page, int size, String sortDirection, String sortBy) {
		
		log.info("{} : This is the sortDirection received in the pageableUtils method.", sortDirection);
		log.info("{} : This is the sortBy received in the pageableUtils method.", sortBy);
		Sort.Direction defaultSortDirection = Direction.ASC;
		Sort.Direction requestedSortDirection;

		// if there is no column name to sortBy return only page and size without sorting.		
		if(sortBy==null || sortBy.isEmpty()) {
			Pageable unsortedPageable = PageRequest.of(page, size);
			return unsortedPageable;
		}
		
		
		/**
		 * Checking if the sortDirection value is present in the URL.
		 * */
		if(sortDirection!=null && !sortDirection.isEmpty() ) {
			if(sortDirection.equalsIgnoreCase("ascending") || sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("a")) {
				requestedSortDirection = Direction.ASC;
				return PageRequest.of(page, size, requestedSortDirection, sortBy);
			}
			else if(sortDirection.equalsIgnoreCase("descending") || sortDirection.equalsIgnoreCase("desc") || sortDirection.equalsIgnoreCase("d")) {
				requestedSortDirection = Direction.DESC;
				return PageRequest.of(page, size, requestedSortDirection, sortBy);
			}
			else {
				throw new IllegalArgumentException("Invalid Sort Direction");
			}
		}
		
		Pageable pageableWithDefaultDirection = PageRequest.of(page, size, defaultSortDirection, sortBy);
		System.out.println("This is the sortBy Value : " + sortBy);
		return pageableWithDefaultDirection;
		
	}
	
	
}
