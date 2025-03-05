package in.shriram.dreambiketwowheelerloan.ledger.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

 
@Entity
@Data
public class Enquiry {

	@Id
	private int customerId;
	private String firstname;
	private String lastName;
	private String address; 
	private String city;
	private int age;
	private String email;
	private String mobileNo;
	private long alternateMobno;
	private String pancardNo;
	private String adharcardNo;
	private String enquiryStatus="Pending";
	private String password;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cibil cibil;
}
