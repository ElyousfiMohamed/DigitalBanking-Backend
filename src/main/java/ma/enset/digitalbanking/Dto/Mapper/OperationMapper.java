package ma.enset.digitalbanking.Dto.Mapper;

import ma.enset.digitalbanking.Dto.OperationDto;
import ma.enset.digitalbanking.Model.Customer;
import ma.enset.digitalbanking.Model.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationMapper {
    public OperationDto toOperationDto(Operation operation) {
        OperationDto operationDto = new OperationDto();
        BeanUtils.copyProperties(operation,operationDto);
        return operationDto;
    }

    public List<OperationDto> toOperationDtos(List<Operation> operations) {
        List<OperationDto> operationDtos = operations.stream().map(operation -> toOperationDto(operation)).collect(Collectors.toList());
        return operationDtos;
    }

    public Operation fromOperationDto(OperationDto operationDto) {
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationDto,operation);
        return operation;
    }

    public List<Operation> fromOperationDtos(List<OperationDto> operationDtos) {
        List<Operation> operations = operationDtos.stream().map(operationDto -> fromOperationDto(operationDto)).collect(Collectors.toList());
        return operations;
    }
}
