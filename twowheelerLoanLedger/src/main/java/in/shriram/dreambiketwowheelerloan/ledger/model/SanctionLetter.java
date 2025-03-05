package in.shriram.dreambiketwowheelerloan.ledger.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class SanctionLetter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sanctionId;
	private Date sanctionDate;
	private String applicantName;
	private String contactDetails;
	private double loanAmtSanctioned;
	private String interestType;
	private float rateOfInterest;
	private int loanTenureInMonth;
	private double monthlyEmiAmount;
	private String modeOfPayment;
	private double onRoadPrice;
	private String status;
	

	@Lob
	@Column
	private byte[] sanctionletterpdf; 




}
