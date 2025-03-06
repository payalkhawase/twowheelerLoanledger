package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
import in.shriram.dreambiketwowheelerloan.ledger.repository.LedgerRepository;
import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;
@Service
public class LedgerServiceImpl implements LedgerServiceI{

	@Autowired
	LedgerRepository lr;

	@Override
	public Set<Ledger> addData(int customerId) {
		
		return null;
	}
}
