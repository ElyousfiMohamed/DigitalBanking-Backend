package ma.enset.digitalbanking.Repository;

import ma.enset.digitalbanking.Model.Customer;
import ma.enset.digitalbanking.Model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
