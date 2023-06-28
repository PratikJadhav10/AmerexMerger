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

	@Query("SELECT t FROM RetrieveDataPage t WHERE t.trade_ref = :trade_ref AND t.date = :date")
	public RetrieveDataPage findByTradeRefAndDate(@Param("trade_ref") String trade_ref,
			@Param("date") LocalDateTime date);

	@Query("SELECT i.messageLoad FROM RetrieveDataPage i WHERE i.bridgeRequestId = :bridgeRequestId AND i.date = :date")
	public RetrieveDataPage findByIdAndDate(@Param("bridgeRequestId") Long bridgeRequestId,
			@Param("date") LocalDateTime date);
	/*
	 * @Query public RetrieveDataPage findBygatewayIdAndDate(String recordCount,
	 * String date);
	 */

}
