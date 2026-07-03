package com.vatManager.vatManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vatManager.vatManager.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

//	Page<Client> findAll(Pageable pageable);

	
//	@Query(value="SELECT * FROM client c ORDER BY c.year_in_bs, c.month_in_bs, c.day_in_bs", nativeQuery = true)
//	Page<Client> findAll(Pageable pageable);
	
	@Query("SELECT c FROM Client c ORDER BY c.yearInBs, c.monthInBs, c.dayInBs")
	Page<Client> findAll(Pageable pageable);
//	@Query("select ")
//	Page<Client> searchClientByQuery(String searchQuery, Pageable pageable);

	@Query(value="SELECT * FROM client c WHERE "
			+ "LOWER(c.foom_name) LIKE :query || '%' "
			+ "OR CAST(c.pan_number AS TEXT) LIKE :query || '%'"
			+ "OR CAST(c.bill_number AS TEXT) LIKE :query || '%'", 
			nativeQuery = true
			)
	Page<Client> searchByQuery( @Param("query")String searchQuery, Pageable pageable);
	
	
	
}
