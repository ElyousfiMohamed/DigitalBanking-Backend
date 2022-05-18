package ma.enset.digitalbanking.Dto;

import lombok.Data;
import ma.enset.digitalbanking.Model.Operation;

import java.util.List;

@Data
public class AccountHistoryDto {
    private String id;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperationDto> operationDtos;
}
