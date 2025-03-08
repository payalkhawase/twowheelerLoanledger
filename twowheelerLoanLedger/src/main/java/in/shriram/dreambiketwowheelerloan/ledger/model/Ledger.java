package in.shriram.dreambiketwowheelerloan.ledger.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ledger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ledgerId;
	private Date ledgerCreatedDate;
	private double totalLoanAmount;
	//@Column(name = "payable_amount_with_interest",nullable=false)
	private double payableAmountwithInterest;
	private int tenure;
	private double monthlyEMI;
	private double amountPaidtillDate=0.0;
	private double remainingAmount;
	private Date nextEmiDatestart;
	private Date nextEmiDateEnd;
	private int defaulterCount;
	private String previousEmitStatus;
	private String currentMonthEmiStatus;
	private Date loanEndDate;
	private String loanStatus;


}
