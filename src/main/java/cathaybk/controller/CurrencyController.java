package cathaybk.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cathaybk.model.dto.receive.MyCyMappingReq;
import cathaybk.model.dto.send.QueryCyRes;
import cathaybk.model.po.MyCurrency;
import cathaybk.service.CurrencyService;
import cathaybk.serviceimpl.CurrencyServiceImpl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


@RestController
@RequestMapping(path = "/cy", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CurrencyController {
	
	private final CurrencyService cyService;
	
	@Autowired
	public CurrencyController(CurrencyServiceImpl cyServiceImpl) {
		this.cyService = cyServiceImpl;
	}

	@GetMapping
	public ResponseEntity<QueryCyRes> readCurrency() throws Exception {
		QueryCyRes body = cyService.readCurrency();
		return ResponseEntity.ok(body);
	}

	@GetMapping(path = "/mapping")
	public ResponseEntity<Map<String, List<MyCurrency>>> readCurrencyMapping() 
			throws Exception {
		Map<String, List<MyCurrency>> body = cyService.readCurrencyMapping();
		return ResponseEntity.ok(body);
	}

	
	@PostMapping(path = "/mapping")
	public ResponseEntity<MyCurrency> createCurrencyMapping(@Valid @RequestBody MyCyMappingReq newCy) 
			throws Exception {
		MyCurrency body = cyService.createCurrencyMapping(newCy);
		return ResponseEntity.created(null).body(body);
	}
	
	@PutMapping(path = "/mapping/{id}")
	public ResponseEntity<MyCurrency> updateCurrencyMapping(
			@Positive(message = "'id' must be a positive integer.") @PathVariable Integer id, 
			@Valid @RequestBody MyCyMappingReq newCy) 
			throws Exception {
		MyCurrency body = cyService.updateCurrencyMapping(id,newCy);
		return ResponseEntity.ok(body);
	}
	
	@DeleteMapping(path = "/mapping/{id}")
	public ResponseEntity<Void> deleteCurrencyMapping(
			@Positive(message = "'id' must be a positive integer.") @PathVariable Integer id) 
					throws Exception {
		cyService.deleteCurrencyMapping(id);
		return ResponseEntity.noContent().build();
	}

}
