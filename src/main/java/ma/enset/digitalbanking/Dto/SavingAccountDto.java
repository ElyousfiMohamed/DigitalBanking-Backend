package ma.enset.digitalbanking.Dto;

import lombok.Data;
import ma.enset.digitalbanking.Model.AccountStatus;
import ma.enset.digitalbanking.Model.Customer;
import ma.enset.digitalbanking.Model.Operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SavingAccountDto extends BankAccountDto{
    private String id;
    private double balance;
    private AccountStatus status;
    private CustomerDto customer;
    private double interestRate;
}
