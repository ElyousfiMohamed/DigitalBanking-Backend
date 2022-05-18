package ma.enset.digitalbanking.Dto.Mapper;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.BankAccountDto;
import ma.enset.digitalbanking.Dto.CurrentAccountDto;
import ma.enset.digitalbanking.Dto.OperationDto;
import ma.enset.digitalbanking.Dto.SavingAccountDto;
import ma.enset.digitalbanking.Model.BankAccount;
import ma.enset.digitalbanking.Model.CurrentAccount;
import ma.enset.digitalbanking.Model.Operation;
import ma.enset.digitalbanking.Model.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankAccountMapper {
    private CustomerMapper customerMapper;

    public CurrentAccountDto toCurrentBankAccountDto(CurrentAccount saved) {
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(saved,currentAccountDto);
        currentAccountDto.setCustomer(customerMapper.toCustomerDto(saved.getCustomer()));
        currentAccountDto.setType(CurrentAccount.class.getSimpleName());
        return currentAccountDto;
    }

    public SavingAccountDto toSavingBankAccountDto(SavingAccount saved) {
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(saved,savingAccountDto);
        savingAccountDto.setCustomer(customerMapper.toCustomerDto(saved.getCustomer()));
        savingAccountDto.setType(SavingAccount.class.getSimpleName());
        return savingAccountDto;
    }

    public CurrentAccount fromCurrentBankAccountDto(CurrentAccountDto saved) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(saved,currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDto(saved.getCustomer()));
        return currentAccount;
    }

    public SavingAccount fromSavingBankAccountDto(SavingAccountDto saved) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(saved,savingAccount);
        savingAccount.setCustomer(customerMapper.fromCustomerDto(saved.getCustomer()));
        return savingAccount;
    }

    public List<CurrentAccountDto> toCurrentBankAccountDtos(List<CurrentAccount> saved) {
        return saved.stream().map(currentAccount -> toCurrentBankAccountDto(currentAccount)).collect(Collectors.toList());
    }

    public List<SavingAccountDto> toSavingBankAccountDtos(List<SavingAccount> saved) {
        return saved.stream().map(savingAccount -> toSavingBankAccountDto(savingAccount)).collect(Collectors.toList());
    }

    public List<CurrentAccount> fromCurrentBankAccountDto(List<CurrentAccountDto> saved) {
        return saved.stream().map(currentAccountDto -> fromCurrentBankAccountDto(currentAccountDto)).collect(Collectors.toList());
    }

    public List<SavingAccount> fromSavingBankAccountDtos(List<SavingAccountDto> saved) {
        return saved.stream().map(savingAccountDto -> fromSavingBankAccountDto(savingAccountDto)).collect(Collectors.toList());
    }
}
