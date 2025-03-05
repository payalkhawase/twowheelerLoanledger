package in.shriram.dreambiketwowheelerloan.ledger.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerAddressId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private permanentAddress paddr;
	
	@OneToOne(cascade = CascadeType.ALL)
	private localAddress laddr;

}
