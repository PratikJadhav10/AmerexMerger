package com.br.amerex.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.amerex.entities.RetrieveDataPage;

public interface RetrieveDataPageRepository extends JpaRepository<RetrieveDataPage, Long> {

	
	  @Query("SELECT r FROM  RetrieveDataPage r") 
	  public List<RetrieveDataPage> viewAllBridgeData();
	  
		/*
		 * @Query public RetrieveDataPage findBygatewayIdAndDate(String recordCount,
		 * String date);
		 */
	 
	 
}
