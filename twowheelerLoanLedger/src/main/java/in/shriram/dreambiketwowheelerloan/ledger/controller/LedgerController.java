package in.shriram.dreambiketwowheelerloan.ledger.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;

@RestController
@RequestMapping("/led")
public class LedgerController {

	@Autowired
	LedgerServiceI lsi;
	
	@PostMapping("/generateledger/{customerId}")
	public ResponseEntity<Set<Ledger>> addData(@PathVariable("customerId") int customerId){
		
		Set<Ledger> ls=lsi.addData(customerId);
		return new ResponseEntity<Set<Ledger>>(ls,HttpStatus.CREATED);
		
	}
}
