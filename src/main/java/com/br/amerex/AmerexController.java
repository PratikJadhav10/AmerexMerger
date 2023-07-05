package com.br.amerex;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.amerex.repo.RetrieveDataPageRepository;
import com.br.amerex.services.RetrieveDataService;
import com.br.amerex.entities.RetrieveDataPage;
import com.br.amerex.entities.SourceSystem;

import poi.ExcelDownloadUtils;

//@CrossOrigin
@Controller
//@ResponseBody
/* @RestController */
@RequestMapping("/api/amerex")
public class AmerexController {

	@Autowired
	private RetrieveDataPageRepository retrieveRepo;

	@Autowired
	private RetrieveDataService retrieveDataService;

	@Autowired
	JdbcTemplate jdbc;

	@GetMapping(value = "/")
	public String home(Model model) {
		model.addAttribute("amerexConfig", new AmerexConfig());
		return "AmerexMerge.html";
	}

	@GetMapping(value = "/br")
	public String br(Model model) {
		return "SourceSystem.html";
	}

	@GetMapping(value = "/search")
	public String search(Model model) {
		return "Search.html";

	}
	
	@GetMapping(value = "/script")
	public String script(Model model) {
		return "script.js";
	}

	@GetMapping(value = "/bridgedata")
	public List<RetrieveDataPage> getData() {
		return retrieveRepo.findAll();
	}

	@GetMapping(value = "/viewallbridge")
	public ResponseEntity<List<RetrieveDataPage>> getAllBridgeData() {
		List<RetrieveDataPage> bridgeData = retrieveDataService.findAllBridgeData();
		return ResponseEntity.ok(bridgeData);
	}

	@GetMapping(value = "/viewallbridgedata")
	public String getallBridgeData(Model model) {
		List<RetrieveDataPage> bridgeData = retrieveDataService.findAllBridgeData();
		model.addAttribute("bridgeData", bridgeData);
		return "bridgepage.html";

	}

	/*
	 * @GetMapping("/{bridgeRequestId}") public RetrieveDataPage
	 * getById(@PathVariable Long bridgeRequestId) { return
	 * retrieveRepo.findById(bridgeRequestId) .orElseThrow(() -> new
	 * IllegalArgumentException("Invalid user Id: " + bridgeRequestId)); }
	 */
	/*
	 * @GetMapping("/getData")
	 * 
	 * @ResponseBody public String getData(@RequestParam("recordCount") String
	 * recordCount, @RequestParam("date") String date) { // Use the userId and date
	 * to query the database and retrieve the desired data RetrieveDataPage
	 * bridgedata = retrieveRepo.findBygatewayIdAndDate(recordCount, date);
	 * 
	 * if (bridgedata != null) { return bridgedata.getMessageLoad(); } else { return
	 * "No data found"; } }
	 */

	@GetMapping("/displayedData")
	@ResponseBody
	public String getXmlData(@RequestParam Long bridgeRequestId) {
		Optional<RetrieveDataPage> retrieveOptional = retrieveRepo.findById(bridgeRequestId);
		if (retrieveOptional.isPresent()) {
			System.out.println("Get mapping");
			RetrieveDataPage bridgedata = retrieveOptional.get();
			return bridgedata.getMessageLoad();
		} else {
			return "Xml data not found";
		}
	}

	/*
	 * @PostMapping("/updated")
	 * 
	 * @ResponseBody public String updateData(@RequestBody UpdateDataRequest
	 * request) { try { Optional<RetrieveDataPage> retrieveOptional =
	 * retrieveRepo.findById(Long.parseLong(request.getRecordCount())); if
	 * (retrieveOptional.isPresent()) { RetrieveDataPage bridgedata =
	 * retrieveOptional.get(); bridgedata.setMessageLoad(request.getUpdatedData());
	 * retrieveRepo.save(bridgedata); return "Data updated successfully"; } else {
	 * return "Xml data not found"; } } catch (NumberFormatException e) { return
	 * "Invalid record count"; }
	 * 
	 * }
	 */

	// Mapping for updating and posting back the data
	@PostMapping("/updateData")
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

	@GetMapping("/csrf-token")
	@ResponseBody
	public String getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		// Set the CSRF token in the response headers
		response.setHeader("X-CSRF-TOKEN", csrfToken.getToken());

		return csrfToken.getToken();
	}

	/*
	 * @GetMapping(value = "/displayedData")
	 * 
	 * @ResponseBody public String getData(@RequestParam("userId") String
	 * userId, @RequestParam("date") String date) { // Use the userId // and date to
	 * // query the database and retrieve the desired data // Perform the necessary
	 * // MySQL queries here and fetch the data based on the provided parameters
	 * 
	 * // Assuming you have a UserRepository injected, you can use it to retrievethe
	 * // data RetrieveDataPage bridgeData =
	 * retrieveRepo.findByUserIdAndRegisteredDate(userId, date);
	 * 
	 * if (user != null) { return user.getUserDesignation(); } else { return
	 * "No data found"; } }
	 */

	@GetMapping(value = "/sourceSystem")
	public ResponseEntity<List<SourceSystem>> sourceSystem(Model model) {
		List<SourceSystem> ss = jdbc.query("select source_system,feed_active from source_system ",
				new BeanPropertyRowMapper<>(SourceSystem.class));
		Map<String, Object> data = new HashMap<>();
		data.put("name", "John");
		data.put("age", 30);
		return ResponseEntity.ok(ss);
	}

	@PostMapping(value = "/updateSourceSystem")
	public ResponseEntity<String> updateSourceSystem(Model model, SourceSystem ss) {
		jdbc.update("update source_system set feed_active =? where source_system= ? ", ss.getFeed_active(),
				ss.getSource_system());
		System.out.println(model);
		return ResponseEntity.ok("");
	}

	@PostMapping(value = "/upload")
	public void upload(MultipartFile file1, MultipartFile file2, HttpServletResponse resp, AmerexConfig config) {
		config.setUp();
		Path tmpP = Paths.get(System.getProperty("user.dir") + "/src/main/resources/tmp");
		Path tf1 = null, tf2 = null;
		try {
			tf1 = Files.createTempFile(tmpP, "header", ".txt");
			tf2 = Files.createTempFile(tmpP, "split", ".txt");
			InputStream i1 = file1.getInputStream();
			Files.write(tf1, StreamUtils.copyToByteArray(file1.getInputStream()), StandardOpenOption.CREATE);
			Files.write(tf2, StreamUtils.copyToByteArray(file2.getInputStream()), StandardOpenOption.CREATE);
			ArrayList<List<String>> list = MergeData.Merge(tf1, tf2, config);
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=mergedData.xlsx");
			try {
				ExcelDownloadUtils.getExcelDownLoad(list, resp.getOutputStream(), config);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Files.deleteIfExists(tf1);
				Files.deleteIfExists(tf2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// return "upload.html";
	}

}
