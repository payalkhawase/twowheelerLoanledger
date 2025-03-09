package in.shriram.dreambiketwowheelerloan.ledger.serviceimpl;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.shriram.dreambiketwowheelerloan.ledger.model.Customer;
import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
import in.shriram.dreambiketwowheelerloan.ledger.model.SanctionLetter;
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


		   if (customer == null || customer.getSanctionletter() == null) {
	            throw new RuntimeException("Customer or sanction letter not found for ID: " + customerId);
	        }
	        
	        SanctionLetter sanctionLetter = customer.getSanctionletter();
	        double payableAmountWithInterest = sanctionLetter.getLoanAmtSanctioned() * Math.pow(1 + (sanctionLetter.getRateOfInterest() / 12) / 100, sanctionLetter.getLoanTenureInMonth());
	        double rateOfInterest = sanctionLetter.getRateOfInterest();
	        int totalMonths = sanctionLetter.getLoanTenureInMonth();
	        
	        if (payableAmountWithInterest <= 0 || rateOfInterest <= 0 || totalMonths <= 0) {
	            throw new RuntimeException("Invalid loan details for Customer ID: " + customerId);
	        }

	        double monthlyInterestRate = (rateOfInterest / 12) / 100;
	        double remainingAmount = payableAmountWithInterest;
	        double amountPaidTillDate = customer.getLed().stream().mapToDouble(Ledger::getAmountPaidtillDate).sum();

	        double previousRemainingAmount = customer.getLed().isEmpty() ? payableAmountWithInterest : customer.getLed().get(customer.getLed().size() - 1).getRemainingAmount();
	        double emi = (previousRemainingAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths))
	                    / (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
	        remainingAmount = Math.max(0, previousRemainingAmount - emi);
	        amountPaidTillDate += emi;
	        
	        Ledger ledger = new Ledger();
	        ledger.setLedgerCreatedDate(LocalDate.now());
	        ledger.setTotalLoanAmount(sanctionLetter.getLoanAmtSanctioned());
	        ledger.setPayableAmountwithInterest(payableAmountWithInterest);
	        ledger.setTenure(totalMonths);
	        ledger.setMonthlyEMI(emi);
	        ledger.setAmountPaidtillDate(amountPaidTillDate);
	        ledger.setRemainingAmount(remainingAmount);
	        ledger.setNextEmiDatestart(LocalDate.now().plusMonths(1));
	        ledger.setNextEmiDateEnd(LocalDate.now().plusMonths(2));
	        
	        int defaulterCount = (int) customer.getLed().stream().filter(l -> "Pending".equals(l.getCurrentMonthEmiStatus())).count();
	        ledger.setDefaulterCount(defaulterCount);
	        
	        String previousEmiStatus = customer.getLed().isEmpty() ? "N/A" : "Paid";
	        ledger.setPreviousEmitStatus(previousEmiStatus);
	        
	        ledger.setCurrentMonthEmiStatus(remainingAmount == 0 ? "Paid" : "Pending");
	        ledger.setLoanEndDate(LocalDate.now().plusMonths(totalMonths));
	        ledger.setLoanStatus(remainingAmount > 0 ? "Open" : "Closed");

	        Ledger savedLedger = lr.save(ledger);
	        customer.getLed().add(savedLedger);
	        cr.save(customer);

	        return customer;
	}
}
