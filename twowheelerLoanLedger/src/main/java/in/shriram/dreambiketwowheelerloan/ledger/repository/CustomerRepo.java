package in.shriram.dreambiketwowheelerloan.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.shriram.dreambiketwowheelerloan.ledger.model.Customer;
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

}
