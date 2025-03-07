package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	
	@Autowired
	RestTemplate rt;

	@Override
	public Set<Ledger> addData(int customerId) {
		Customer cust=rt.getForObject("http://localhost:7777/apploan/getaCustomer/"+customerId, Customer.class);
		
		if(!cust.getLoandisburst().equals(null)) {
			
			
			double totalLoanAmount=cust.getSanctionletter().getLoanAmtSanctioned();
		    float monthlyInterest=cust.getSanctionletter().getRateOfInterest();
		    int tenureMonths = cust.getSanctionletter().getLoanTenureInMonth();
		    
		    double rateDecimal = monthlyInterest / 100;
		    
		    double totalAmountPayablEmi = totalLoanAmount * Math.pow(1 + (rateDecimal / 12), 12 * tenureMonths);
      
			int requiredTenure=cust.getRequiredTenure();
			
			for(int i=1;i<=12;i++) {
				
				Ledger lo=new Ledger();
				lo.setLedgerCreatedDate(new Date());
				lo.setTotalLoanAmount(totalLoanAmount);
				lo.setPayableAmountwithInterest(totalAmountPayablEmi*12);
				lo.setTenure(requiredTenure);
				lo.setMonthlyEMI(totalAmountPayablEmi);
				lo.setAmountPaidtillDate(new Date());
				lo.setRemainingAmount(totalLoanAmount);
				lo.setNextEmiDatestart(new Date());
				lo.setNextEmiDateEnd(new Date());
				lo.getDefaulterCount();
				lo.setPreviousEmitStatus("Not Paid");
				lo.setCurrentMonthEmiStatus("Not Paid");
				lo.setLoanEndDate("12-March-2026");
				lo.setLoanStatus("loan open");
				
				Ledger l=lr.save(lo);
				
			}
		}
		return null;
		
		
	}
}
