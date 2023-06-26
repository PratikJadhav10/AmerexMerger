package com.br.amerex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.amerex.entities.RetrieveDataPage;
import com.br.amerex.entities.TFInterfaceBean;
import com.br.amerex.exceptions.NoSuchBridgeIdException;
import com.br.amerex.repo.RetrieveDataPageRepository;
import com.br.amerex.services.RetrieveDataService;

@RestController
@RequestMapping("/data")
public class TFInterface {
	@Autowired
	private final RetrieveDataService retrieveDataService;

	@Autowired
	public TFInterface(RetrieveDataService retrieveDataService) {
		this.retrieveDataService = retrieveDataService;
	}

	@Autowired
	private RetrieveDataPageRepository retrieveRepo;

	@Autowired
	JdbcTemplate jdbc;

	@GetMapping(value = "/viewallbridge")
	public ResponseEntity<List<RetrieveDataPage>> getAllBridgeData() {
		List<RetrieveDataPage> bridgeData = retrieveDataService.findAllBridgeData();
		return ResponseEntity.ok(bridgeData);
	}

	// This returns whole object related to the id
	@GetMapping("/{bridgeRequestId}")
	public RetrieveDataPage getXmlData(@PathVariable Long bridgeRequestId) throws NoSuchBridgeIdException {
		System.out.println("getXmlData()");
		RetrieveDataPage retrieveDataPage = retrieveRepo.findById(bridgeRequestId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + bridgeRequestId));
		return retrieveDataPage;
		// return retrieveRepo.findById(bridgeRequestId).orElseThrow(() -> new
		// IllegalArgumentException("Invalid user Id: " + bridgeRequestId));
	}

	// This returns only message load data(xml data) of the particular id
	@GetMapping("/new/{bridgeRequestId}")
	public String getXmlData1(@PathVariable Long bridgeRequestId) throws NoSuchBridgeIdException {
		System.out.println("getXmlData()");
		Optional<RetrieveDataPage> retrieveData = retrieveRepo.findById(bridgeRequestId);
		if (retrieveData.isPresent()) {
			System.out.println("Get mapping");
			RetrieveDataPage bridgedata = retrieveData.get();
			return bridgedata.getMessageLoad();
		} else {
			return "Xml data not found";
		}
	}

	@PutMapping("/new/update/messageload")
	public String updateXml(@RequestParam Long bridgeRequestId, @RequestBody String updatedData)
			throws NoSuchBridgeIdException {
		System.out.println("Putmapping");
		RetrieveDataPage retrieveData = retrieveRepo.findById(bridgeRequestId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id:" + bridgeRequestId));
		retrieveData.setMessageLoad(updatedData);
		retrieveRepo.save(retrieveData);

		return "Data updated successfully";

	}

	@PostMapping("/new/updateData")
	public ResponseEntity<String> updateXmlData(@RequestParam("bridgeRequestId") Long bridgeRequestId,
			@RequestParam("updatedData") String updatedData) {
		System.out.println("Post mapping1");
		Optional<RetrieveDataPage> retrieveOptional = retrieveRepo.findById(bridgeRequestId);
		if (retrieveOptional.isPresent()) {
			RetrieveDataPage bridgedata = retrieveOptional.get();
			bridgedata.setMessageLoad(updatedData);
			retrieveRepo.save(bridgedata);
			System.out.println("Post mapping2");
			return ResponseEntity.ok("Data updated and posted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xml data not found");
		}
	}

	/*
	 * @GetMapping("/displayedData/{id}")
	 * 
	 * @ResponseBody public String getXmlData(@PathVariable("id") Long
	 * bridgeRequestId) { Optional<RetrieveDataPage> retrieveOptional =
	 * retrieveRepo.findById(bridgeRequestId); if (retrieveOptional.isPresent()) {
	 * System.out.println("Get mapping1"); RetrieveDataPage bridgedata =
	 * retrieveOptional.get(); return bridgedata.getMessageLoad(); } else { return
	 * "Xml data not found"; } }
	 */

	/*
	 * @GetMapping(path = "/viewAllTrade") public
	 * ResponseEntity<List<TFInterfaceBean>> getAllCustomer() { //
	 * logger.info("getAllCustomer() method is called"); List<TFInterfaceBean>
	 * result = retrieveDataService.findAllTrade(); if (result != null) return new
	 * ResponseEntity<>(result, HttpStatus.OK); else return new
	 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * @GetMapping(path = "/viewAllBridgeData") public
	 * ResponseEntity<List<RetrieveDataPage>> getAllbridgeData() { // //
	 * logger.info("getAllCustomer() method is called"); List<RetrieveDataPage>
	 * result = retrieveDataService.findAllBridgeData(); if (result != null) return
	 * new ResponseEntity<>(result, HttpStatus.OK); else return new
	 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }
	 */

	@GetMapping(value = "/interface")
	public String home2(Model model) {
		model.addAttribute("defaultTranID", "RP0030600296321");
		System.out.println("interface was called");
		return "tfinterface.html";
	}

	@GetMapping(value = "/interfaceFetch")
	public ResponseEntity<List<TFInterfaceBean>> interfaceFetch(String tran_id) {
		Object[] params = { tran_id };
		List<TFInterfaceBean> x = jdbc.query("select * from tf_interface where trade_ref = ?", params,
				new BeanPropertyRowMapper<>(TFInterfaceBean.class));
		return ResponseEntity.ok(x);
	}

}
