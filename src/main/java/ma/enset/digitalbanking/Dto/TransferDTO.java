package ma.enset.digitalbanking.Dto;


import lombok.Data;

@Data
public class TransferDTO {
    private String accountDestination;
    private String accountSource;
    private double amount;
    private String description;
}
