package com.br.amerex.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.amerex.entities.RetrieveDataPage;

public interface RetrieveDataPageRepository extends JpaRepository<RetrieveDataPage, Long> {

	@Query("SELECT r FROM  RetrieveDataPage r")
	public List<RetrieveDataPage> viewAllBridgeData();

	@Query("SELECT e FROM RetrieveDataPage e WHERE e.tradeRef = :tradeRef AND e.date = :dateTime")
	public RetrieveDataPage findByTradeRefAndDate(@Param("tradeRef") String tradeRef,
			@Param("dateTime") LocalDateTime dateTime);

	@Query("SELECT i FROM RetrieveDataPage i WHERE i.bridgeRequestId = :bridgeRequestId AND i.date = :date")
	public RetrieveDataPage findByIdAndDate(@Param("bridgeRequestId") Long bridgeRequestId,
			@Param("date") LocalDateTime date);
	
	@Query("SELECT r FROM RetrieveDataPage r WHERE r.reqFileName = :reqFileName")
	public RetrieveDataPage findByReqFileName(@Param("reqFileName") String reqFileName);
	/*
	 * @Query public RetrieveDataPage findBygatewayIdAndDate(String recordCount,
	 * String date);
	 */

}
//@Query("SELECT t FROM RetrieveDataPage t WHERE t.tradeRef = :tradeRef AND t.date = :date")