package com.br.amerex.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.amerex.entities.RetrieveDataPage;
import com.br.amerex.entities.TFInterfaceBean;
import com.br.amerex.repo.RetrieveDataPageRepository;
import com.br.amerex.repo.TFInterfaceRepository;

@Service
public class RetrieveDataService {

	@Autowired
	private final TFInterfaceRepository brRepository;

	@Autowired
	private RetrieveDataPageRepository retrieveRepo;

	@Autowired
	public RetrieveDataService(TFInterfaceRepository brRepository) {
		this.brRepository = brRepository;
	}

	public List<TFInterfaceBean> findAllTrade() {
		return brRepository.viewAllTrade();
	}

	public List<RetrieveDataPage> findAllBridgeData() {
		return retrieveRepo.viewAllBridgeData();
	}

	public String fetchMessageLoad(String trade_ref, LocalDateTime date) {
		RetrieveDataPage retrieveobj = retrieveRepo.findByTradeRefAndDate(trade_ref, date);
		if (retrieveobj != null) {
			return retrieveobj.getMessageLoad();
		} else
			return "No data found";

	}
	
	public String fetchMessageLoad2(Long bridgeRequestId, LocalDateTime date) {
		RetrieveDataPage retrieveobj = retrieveRepo.findByIdAndDate(bridgeRequestId, date);
		if (retrieveobj != null) {
			return retrieveobj.getMessageLoad();
		} else
			return "No data found1";

	}

	/*
	 * public List<TFInterfaceBean> searchData(String trade_ref, Timestamp
	 * trade_date) { return brRepository.findBytrade_refAndDate(trade_ref,
	 * trade_date); }
	 */

}
