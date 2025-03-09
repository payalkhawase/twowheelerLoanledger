package in.shriram.dreambiketwowheelerloan.ledger.model;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity 
@Data
public class Customer {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	private String customerName; 
	private String customerDateOfBirth;
	private int customerAge; 
	private String customerGender;
	private String customerMobileNumber;
	private double customerAdditionalMobileNumber;
	private double customerAmountPaidForBike;
	private double customerTotalLoanRequired;
	private String customerEmail;
	private String password;
	private String loanStatus="Submit";
	private double onRoadPrice;
	private int requiredTenure;
	private String interesType="Compound Interest";

	
	@OneToOne(cascade = CascadeType.ALL)
	private AllPersonalDocuments personalDoc;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DependentInformation depInfo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress custAddr;
	
	@OneToOne(cascade = CascadeType.MERGE ,orphanRemoval = false)
	@JoinColumn(name = "cibilId")
	private Cibil cibil;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AccountDetails acdetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GuarantorDetails gdetails;
 
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDisbursement loandisburst;
	
	@OneToMany(cascade = CascadeType.ALL)

	private List <Ledger> led;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sanctionId")
	private SanctionLetter sanctionletter;

	@OneToOne(cascade = CascadeType.ALL)
	private CustomerVerification custVerification;


}
