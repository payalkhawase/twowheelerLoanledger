package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.shriram.dreambiketwowheelerloan.ledger.model.Customer;
import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
import in.shriram.dreambiketwowheelerloan.ledger.repository.LedgerRepository;
import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;
@Service
public class LedgerServiceImpl implements LedgerServiceI{

	@Autowired
	LedgerRepository lr;
	
	
}
