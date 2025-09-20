package com.vatManager.vatManager.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageableUtils {

	
	
	public static Pageable createPageable(int page, int size, String sortDirection, String sortBy) {
		
		Sort.Direction defaultSortDirection = Direction.ASC;
		Sort.Direction requestedSortDirection;

		// if there is no column name to sortBy return only page and size without sorting.		
		if(sortBy==null || sortBy.isEmpty()) {
			Pageable unsortedPageable = PageRequest.of(page, size);
			return unsortedPageable;
		}
		
		
		if(sortDirection!=null && !sortDirection.isEmpty() ) {
			if(sortBy.equalsIgnoreCase("ascending") || sortBy.equalsIgnoreCase("asc") || sortBy.equalsIgnoreCase("a")) {
				requestedSortDirection = Direction.ASC;
				return PageRequest.of(page, size, requestedSortDirection, sortBy);
			}
			else if(sortBy.equalsIgnoreCase("descending") || sortBy.equalsIgnoreCase("desc") || sortBy.equalsIgnoreCase("d")) {
				requestedSortDirection = Direction.DESC;
				return PageRequest.of(page, size, requestedSortDirection, sortBy);
			}
			else {
				throw new IllegalArgumentException("Invalid Sort Direction");
			}
		}
		
		Pageable pageableWithDefaultDirection = PageRequest.of(page, size, defaultSortDirection, sortBy);
		return pageableWithDefaultDirection;
		
	}
	
	
}
