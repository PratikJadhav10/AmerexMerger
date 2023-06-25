package com.br.amerex.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.amerex.entities.TFInterfaceBean;

public interface TFInterfaceRepository extends JpaRepository<TFInterfaceBean, Integer> {

	@Query("SELECT t from  TFInterfaceBean t")
	List<TFInterfaceBean> viewAllTrade();

	/*
	 * @Query List<TFInterfaceBean> findBytrade_refAndDate(String trade_ref,
	 * Timestamp trade_date);
	 */

}
