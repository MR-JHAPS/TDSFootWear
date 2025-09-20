package com.vatManager.vatManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vatManager.vatManager.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

	Page<Client> findAll(Pageable pageable);
	
	
	
	
	
}
