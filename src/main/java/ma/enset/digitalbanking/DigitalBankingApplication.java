package ma.enset.digitalbanking;

import ma.enset.digitalbanking.Dto.CustomerDto;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Model.BankAccount;
import ma.enset.digitalbanking.Model.CurrentAccount;
import ma.enset.digitalbanking.Model.SavingAccount;
import ma.enset.digitalbanking.Service.DigitalBankingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DigitalBankingService digitalBankingService) {
        return args -> {
            Stream.of("Mohamed", "Oussama", "Fatima", "Firdaousse").forEach(name ->
                digitalBankingService.saveCustomer(new CustomerDto(null,name,name+"@gmail.com"))
            );

            digitalBankingService.getCustomers().forEach(c-> {
                try {
                    digitalBankingService
                            .saveBankAccount(1000+Math.random()*50000, CurrentAccount.class.getSimpleName(),c.getId());
                    digitalBankingService
                            .saveBankAccount(1000+Math.random()*50000, SavingAccount.class.getSimpleName(),c.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        };
    }
}
