package ma.enset.digitalbanking.Repository;

import ma.enset.digitalbanking.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
}
