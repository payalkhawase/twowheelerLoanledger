package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.shriram.dreambiketwowheelerloan.ledger.model.Customer;
import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;

import in.shriram.dreambiketwowheelerloan.ledger.repository.CustomerRepo;
import in.shriram.dreambiketwowheelerloan.ledger.repository.LedgerRepository;
import in.shriram.dreambiketwowheelerloan.ledger.servicei.LedgerServiceI;
@Service
public class LedgerServiceImpl implements LedgerServiceI{

	@Autowired
	LedgerRepository lr;
	

	@Autowired
	RestTemplate rt;

	@Autowired
	CustomerRepo cr;
	
	@Override
	public Customer addData(int customerId) {
		Customer cust=rt.getForObject("http://localhost:7777/apploan/getaCustomer/"+customerId, Customer.class);
		
		if (cust.getLoandisburst() != null) {
 
			
			
			double totalLoanAmount=cust.getSanctionletter().getLoanAmtSanctioned();
		    float monthlyInterest=cust.getSanctionletter().getRateOfInterest();
		    int tenureMonths = cust.getSanctionletter().getLoanTenureInMonth();
		    
		    double rateDecimal = (monthlyInterest / 100) / 12;
		    double emi = (totalLoanAmount * rateDecimal * Math.pow(1 + rateDecimal, tenureMonths)) / (Math.pow(1 + rateDecimal, tenureMonths) - 1);

		   // double totalAmountPayablEmi = totalLoanAmount * Math.pow(1 + (rateDecimal / 12), 12 * tenureMonths);
      
			int requiredTenure=cust.getRequiredTenure();
			
				Ledger lo=new Ledger();
				lo.setLedgerCreatedDate(new Date());
				lo.setTotalLoanAmount(totalLoanAmount);
				lo.setPayableAmountwithInterest(emi);
				lo.setTenure(tenureMonths);
				lo.setMonthlyEMI(emi);

				// Calculate Amount Paid Till Date
				
				
				Date today=new Date();
				if (lr.selectPayableAmountwithInterest() != null)
				lo.setAmountPaidtillDate(lr.selectPayableAmountwithInterest());
				
				Double amountPaid = lr.selectPayableAmountwithInterest();
				if (amountPaid == null) {
				    amountPaid = 0.0;
				}
				lo.setRemainingAmount(totalLoanAmount - amountPaid);
				
				Date nextEmiDate = new Date(today.getTime() + (1000L * 60 * 60 * 24 * 30L));
				lo.setNextEmiDatestart(nextEmiDate);
				
				// Calculate next EMI end date (5 days after next EMI date)

				Date nextEmiDateEnd = new Date(nextEmiDate.getTime() + (1000L * 60 * 60 * 24 * 5L));
				lo.setNextEmiDateEnd(nextEmiDateEnd);
				
				// Calculate EMI statuses
				
				lo.setDefaulterCount(0);
				lo.setPreviousEmitStatus("Not Paid");
				lo.setCurrentMonthEmiStatus("Not Paid");
				
				// Calculate loan end date (adding loan tenure in months)
				Date loanEndDate = new Date(today.getTime() + (1000L * 60 * 60 * 24 * 30L * tenureMonths));
				
				lo.setLoanEndDate(loanEndDate);
				if (lo.getRemainingAmount() <= 0) { 
				    lo.setLoanStatus("closed"); 
				} else { 
				    lo.setLoanStatus("loan open"); 
				}
				
				Ledger l=lr.save(lo);
				if (cust.getLed() == null) {
				    cust.setLed(new ArrayList());
				}
				cust.getLed().add(l);

				cr.save(cust);
			
		}
		return cust;
		
		
	}
}
