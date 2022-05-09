package ma.enset.digitalbanking.Repository;

import ma.enset.digitalbanking.Model.BankAccount;
import ma.enset.digitalbanking.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
