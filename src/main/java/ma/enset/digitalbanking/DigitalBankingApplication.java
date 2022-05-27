package ma.enset.digitalbanking;

import ma.enset.digitalbanking.Dto.BankAccountDto;
import ma.enset.digitalbanking.Dto.CurrentAccountDto;
import ma.enset.digitalbanking.Dto.CustomerDto;
import ma.enset.digitalbanking.Dto.SavingAccountDto;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Model.BankAccount;
import ma.enset.digitalbanking.Model.CurrentAccount;
import ma.enset.digitalbanking.Model.SavingAccount;
import ma.enset.digitalbanking.Security.Service.IServiceSecurity;
import ma.enset.digitalbanking.Service.DigitalBankingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(DigitalBankingService digitalBankingService, IServiceSecurity serviceSecurity) {
        return args -> {
            Stream.of("Mohamed", "Oussama", "Fatima", "Firdaousse").forEach(name ->
                digitalBankingService.saveCustomer(new CustomerDto(null,name,name+"@gmail.com","https://www.bootdey.com/app/webroot/img/Content/avatar/avatar7.png"))
            );

            digitalBankingService.getCustomers().forEach(c-> {
                try {
                    for (int i = 0; i < 2; i++) {
                        digitalBankingService
                                .saveCurrentAccount(1000+Math.random()*50000, 500,c.getId());
                        digitalBankingService
                                .saveSavingAccount(1000+Math.random()*50000, Math.random()*4+1,c.getId());
                    }
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });

            List<BankAccountDto> bankAccounts = digitalBankingService.getBankAccounts();
            for (BankAccountDto bankAccount : bankAccounts) {
                for (int i = 0; i < 10; i++) {
                    String id;
                    if(bankAccount instanceof CurrentAccountDto) {
                        id = ((CurrentAccountDto) bankAccount).getId();
                    }else {
                        id = ((SavingAccountDto) bankAccount).getId();
                    }
                    digitalBankingService.credit(id,1000+Math.random()*50000,"Credit");
                    digitalBankingService.debit(id,1000+Math.random()*5000,"Debit");
                }
            }

            serviceSecurity.saveNewUser("mohamed", "1234", "1234");
            serviceSecurity.saveNewUser("hamza", "1234", "1234");

            serviceSecurity.saveNewRole("USER", "");
            serviceSecurity.saveNewRole("ADMIN", "");

            serviceSecurity.addRoleToUser("mohamed", "USER");
            serviceSecurity.addRoleToUser("mohamed", "ADMIN");
            serviceSecurity.addRoleToUser("hamza", "USER");
        };
    }
}
