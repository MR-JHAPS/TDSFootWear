package com.vatManager.vatManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vatManager.vatManager.entity.Kharid;

@Repository
public interface KharidRepository extends JpaRepository<Kharid, Integer>{

Page<Kharid> findAll(Pageable pageable);
	
//	@Query("select ")
//	Page<Client> searchClientByQuery(String searchQuery, Pageable pageable);

	@Query(value="SELECT * FROM kharid k WHERE "
			+ "LOWER(k.foom_name) LIKE :query || '%' "
			+ "OR CAST(k.pan_number AS TEXT) LIKE :query || '%'"
			+ "OR CAST(k.bill_number AS TEXT) LIKE :query || '%'", 
			nativeQuery = true
			)
	Page<Kharid> searchByQuery( @Param("query")String searchQuery, Pageable pageable);
	
	
	
	
}
