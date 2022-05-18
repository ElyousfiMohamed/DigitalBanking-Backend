package ma.enset.digitalbanking.Dto;

import lombok.Data;
import ma.enset.digitalbanking.Model.AccountStatus;

@Data
public class CurrentAccountDto extends BankAccountDto{
    private String id;
    private double balance;
    private AccountStatus status;
    private CustomerDto customer;
    private double overDraft;
}
