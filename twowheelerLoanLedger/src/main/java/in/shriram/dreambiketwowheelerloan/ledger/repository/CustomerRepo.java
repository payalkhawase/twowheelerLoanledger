package in.shriram.dreambiketwowheelerloan.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.shriram.dreambiketwowheelerloan.ledger.model.Customer;
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	@Query(value="SELECT SUM(payable_amountwith_interest) as tillPayble  FROM  twowheelerloan.ledger inner join  twowheelerloan.customer_led on ledger.ledger_id=customer_led.led_ledger_id where customer_led.customer_customer_id=1 and ledger.previous_emit_status='paid'",nativeQuery = true)
	public Double selectPayableAmountwithInterest();
}
