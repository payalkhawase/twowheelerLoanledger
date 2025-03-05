package in.shriram.dreambiketwowheelerloan.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.shriram.dreambiketwowheelerloan.ledger.model.Ledger;
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Integer>{

}
