package in.shriram.dreambiketwowheelerloan.ledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;

@RestController
@RequestMapping("/led")
public class LedgerController {

	@Autowired
	LedgerServiceI lsi;
	
	
}
