package in.shriram.dreambiketwowheelerloan.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Integer>{

	//@Query(value = "SELECT SUM(payableAmountwithInterest) FROM Ledger WHERE customerId", nativeQuery = true) 
	//public double selectPayableAmountwithInterest();
	
	@Query(value = "SELECT SUM(payable_amount_with_interest) FROM Ledger WHERE customer_id = :customerId", nativeQuery = true)
	Double selectPayableAmountwithInterest(@Param("customerId") int customerId);
}
