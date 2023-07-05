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
	
	public String fetchByReqFileName(String reqFileName) {
		RetrieveDataPage retrieveobj = retrieveRepo.findByReqFileName(reqFileName);
		if (retrieveobj !=null) {
			return retrieveobj.getMessageLoad();
		}else
		return "No data found from reqFileName";
		
	}

	public String fetchMessageLoad(String tradeRef, LocalDateTime dateTime) {
		System.out.println("tradeRef:" + tradeRef + ", date:" + dateTime);
		RetrieveDataPage retrieveobj = retrieveRepo.findByTradeRefAndDate(tradeRef, dateTime);
		System.out.println(retrieveobj);
		if (retrieveobj != null) {
			return retrieveobj.getMessageLoad();
		} else
			return "No data found from trade_ref";

	}

	public String fetchMessageLoad2(Long bridgeRequestId, LocalDateTime date) {
		System.out.println("id:" + bridgeRequestId + ", date:" + date);
		RetrieveDataPage retrieveobj = retrieveRepo.findByIdAndDate(bridgeRequestId, date);
		System.out.println(retrieveobj);
		if (retrieveobj != null) {
			return retrieveobj.getMessageLoad();
		} else
			return "No data found from id";

	}

	/*
	 * public List<TFInterfaceBean> searchData(String trade_ref, Timestamp
	 * trade_date) { return brRepository.findBytrade_refAndDate(trade_ref,
	 * trade_date); }
	 */

}
