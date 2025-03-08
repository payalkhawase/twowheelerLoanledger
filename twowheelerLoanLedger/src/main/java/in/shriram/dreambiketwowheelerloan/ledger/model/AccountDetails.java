package in.shriram.dreambiketwowheelerloan.ledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;
	private String accountType;
	private String bankName;
	private String ifscCode;
	private double accountBalance;
	private String accountHolderName;
	private String accountStatus;
	private long accountNumber;


}
