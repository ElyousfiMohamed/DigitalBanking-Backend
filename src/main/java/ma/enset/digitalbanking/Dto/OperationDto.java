package ma.enset.digitalbanking.Dto;

import lombok.Data;
import ma.enset.digitalbanking.Model.BankAccount;
import ma.enset.digitalbanking.Model.OperationType;

import java.util.Date;

@Data
public class OperationDto {
    private Long id;
    private Date date;
    private double amount;
    private OperationType operationType;
    private String desciption;
}
