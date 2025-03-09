package in.shriram.dreambiketwowheelerloan.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Integer>{

	//@Query(value = "SELECT SUM(payableAmountwithInterest) FROM Ledger WHERE customerId", nativeQuery = true) 
	//public double selectPayableAmountwithInterest();
	
	//@Query(value = "SELECT SUM(payable_amount_with_interest) FROM Ledger WHERE customer_id = :customerId", nativeQuery = true)
	//Double selectPayableAmountwithInterest(@Param("customerId") int customerId);
	
<<<<<<< HEAD
	@Query(value="SELECT SUM(l.payable_amountwith_interest) FROM ledger l INNER JOIN customer_led cl ON l.ledger_id = cl.led_ledger_id WHERE cl.customer_customer_id = :customerId AND l.previous_emit_status = 'paid'", nativeQuery = true)
	Double selectPayableAmountwithInterest();

=======
//	@Query(value="SELECT SUM(payable_amountwith_interest) FROM  twowheelerloan.ledger inner join  twowheelerloan.customer_led on ledger.ledger_id=customer_led.led_ledger_id where customer_led.customer_customer_id=1 and ledger.previous_emit_status='paid'",nativeQuery = true)
//	public Double selectPayableAmountwithInterest( );

	
>>>>>>> branch 'payal' of https://github.com/payalkhawase/twowheelerLoanledger.git
}
