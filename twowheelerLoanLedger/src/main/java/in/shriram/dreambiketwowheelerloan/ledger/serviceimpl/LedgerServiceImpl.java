package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.shriram.dreambiketwowheelerloan.ledger.repository.LedgerRepository;
import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;
@Service
public class LedgerServiceImpl implements LedgerServiceI{

	@Autowired
	LedgerRepository lr;
}
