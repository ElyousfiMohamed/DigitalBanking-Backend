package ma.enset.digitalbanking.Dto;

import lombok.Data;
import ma.enset.digitalbanking.Model.Customer;

@Data
public class BankAccountDto {
    private String type;
    private CustomerDto customer;
}
