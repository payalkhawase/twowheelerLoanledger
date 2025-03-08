package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import java.time.LocalDate;
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
		
		
		Customer customer=rt.getForObject("http://localhost:7777/apploan/getaCustomer/"+customerId, Customer.class);
		
		

		// Initialize ledger list if null
		if (customer.getLed() == null) {
			customer.setLed(new ArrayList());
		}

		// Fetch loan details
		Double principal = customer.getSanctionletter().getLoanAmtSanctioned();
		Double rateOfInterest = (double) customer.getSanctionletter().getRateOfInterest();
		Integer tenureInYear = customer.getSanctionletter().getLoanTenureInMonth();

		if (principal == null || rateOfInterest == null || tenureInYear == null) {
			throw new RuntimeException("Required loan details are missing for Customer ID: " + customerId);
		}

		int totalMonths = customer.getSanctionletter().getLoanTenureInMonth();
		
		double monthlyInterestRate = (rateOfInterest / 12 )/ 100;

		// EMI Calculation (Reducing Balance Method)
		double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths))
				/ (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
		Double payableAmountWithInterest = emi * totalMonths; // Total payable amount

		// Create new Ledger entry
		Ledger ledger = new Ledger();
		ledger.setLedgerCreatedDate(LocalDate.now().toString());
		ledger.setTotalLoanAmount(customer.getLoandisburst().getTotalAmount());
		ledger.setPayableAmountwithInterest(payableAmountWithInterest);
		ledger.setTenure(tenureInYear);
		ledger.setMonthlyEMI(emi);

		// Calculate total amount paid till date
		double lastAmountPaid = customer.getLed().isEmpty() ? 0
			: customer.getLed().get(customer.getLed().size() - 1).getAmountPaidtillDate();
		
		//double lastAmountPaid = customer.getLed().isEmpty() ? 0
		//	    : ((Ledger) customer.getLed()).getAmountPaidtillDate();

		double amountPaidTillDate = lastAmountPaid + payableAmountWithInterest;
		ledger.setAmountPaidtillDate(amountPaidTillDate);

		// Calculate remaining amount
//		Double remainingAmount = payableAmountWithInterest - amountPaidTillDate;
		Double remainingAmount = Math.max(0, payableAmountWithInterest - amountPaidTillDate);

		ledger.setRemainingAmount(remainingAmount);

		// Set EMI Dates
		LocalDate lastEmiDate = customer.getLed().isEmpty() ? LocalDate.now()
			: LocalDate.parse(customer.getLed().get(customer.getLed().size() - 1).getLedgerCreatedDate());
		//LocalDate lastEmiDate = customer.getLed().isEmpty() ? LocalDate.now()
			//    : LocalDate.parse(customer.getLed().stream().reduce((first, second) -> second).get().getLedgerCreatedDate());
		
		LocalDate nextEmiStartDate = lastEmiDate.plusMonths(1);
		LocalDate nextEmiEndDate = nextEmiStartDate.plusMonths(1);
		ledger.setNextEmiDatestart(nextEmiStartDate.toString());
		ledger.setNextEmiDateEnd(nextEmiEndDate.toString());

		// Defaulter Count Tracking
		long missedEmis = customer.getLed().stream().filter(l -> "Missed".equals(l.getCurrentMonthEmiStatus()))
				.count();
		int defaulterCount = (int) missedEmis;
		if ( payableAmountWithInterest== 0) {
			defaulterCount++;
		}
		ledger.setDefaulterCount(defaulterCount);

		// Set EMI status
		String previousEmiStatus = customer.getLed().isEmpty() ? "N/A"
				: customer.getLed().get(customer.getLed().size() - 1).getCurrentMonthEmiStatus();
		
		//String previousEmiStatus = customer.getLed().isEmpty() ? "N/A"
			//    : customer.getLed().stream().reduce((first, second) -> second).get().getCurrentMonthEmiStatus();
		ledger.setPreviousEmitStatus(previousEmiStatus);
		ledger.setCurrentMonthEmiStatus(payableAmountWithInterest == 0 ? "Missed" : "Paid");

		// Loan end date calculation
		//LocalDate firstEmiDate = customer.getLed().isEmpty() ? LocalDate.now()
		//		: LocalDate.parse(customer.getLed().get(0).getLedgerCreatedDate());
		
		LocalDate firstEmiDate = customer.getLed().isEmpty() ? LocalDate.now()
			    : LocalDate.parse(customer.getLed().iterator().next().getLedgerCreatedDate());
		LocalDate loanEndDate = firstEmiDate.plusYears(tenureInYear);
		ledger.setLoanEndDate(loanEndDate.toString());

		// Set loan status
		ledger.setLoanStatus(remainingAmount > 0 ? "Ongoing" : "Completed");
          Ledger lo=lr.save(ledger);
		// Add ledger entry
		customer.getLed().add(lo);

		// Save customer with updated ledger
		
		//customer.setLed(lo);
		cr.save(customer);
		
		return customer;
		
		
		
//		Customer cust=rt.getForObject("http://localhost:7777/apploan/getaCustomer/"+customerId, Customer.class);
//		
//		if(!cust.getLoandisburst().equals(null)) {
//			
//			
//			double totalLoanAmount=cust.getSanctionletter().getLoanAmtSanctioned();
//		    float monthlyInterest=cust.getSanctionletter().getRateOfInterest();
//		    int tenureMonths = cust.getSanctionletter().getLoanTenureInMonth();
//		    
//		    double rateDecimal = (monthlyInterest / 12)/100;
//		    double   emi = (totalLoanAmount * rateDecimal * Math.pow(1 + rateDecimal,tenureMonths)) / (Math.pow(1 + rateDecimal, tenureMonths) - 1);
//		   // double totalAmountPayablEmi = totalLoanAmount * Math.pow(1 + (rateDecimal / 12), 12 * tenureMonths);
//      
//			int requiredTenure=cust.getRequiredTenure();
//			
//				Ledger lo=new Ledger();
//				lo.setLedgerCreatedDate(new Date());
//				lo.setTotalLoanAmount(totalLoanAmount);
//				lo.setPayableAmountwithInterest(emi);
//				lo.setTenure(tenureMonths);
//				lo.setMonthlyEMI(totalLoanAmount/tenureMonths);
//				// Calculate Amount Paid Till Date
//				
//				
//				Date today=new Date();
//				if(!(lr.selectPayableAmountwithInterest().equals(null)))	
//				lo.setAmountPaidtillDate(lr.selectPayableAmountwithInterest());
//				
//				lo.setRemainingAmount(totalLoanAmount);
//				
//				Date nextEmiDate = new Date(today.getTime() + (1000L * 60 * 60 * 24 * 30L));
//				lo.setNextEmiDatestart(nextEmiDate);
//				
//				// Calculate next EMI end date (5 days after next EMI date)
//
//				Date nextEmiDateEnd = new Date(nextEmiDate.getTime() + (1000L * 60 * 60 * 24 * 5L));
//				lo.setNextEmiDateEnd(nextEmiDateEnd);
//				
//				// Calculate EMI statuses
//				
//				lo.setDefaulterCount(0);
//				lo.setPreviousEmitStatus("Not Paid");
//				lo.setCurrentMonthEmiStatus("Not Paid");
//				
//				// Calculate loan end date (adding loan tenure in months)
//				Date loanEndDate = new Date(today.getTime() + (1000L * 60 * 60 * 24 * 30L * tenureMonths));
//				
//				lo.setLoanEndDate(loanEndDate);
//				lo.setLoanStatus("loan open");
//				
//				Ledger l=lr.save(lo);
//				cust.getLed().add(l);
//				cr.save(cust);
			

		//return cr.save();;
		
		
	}
}
