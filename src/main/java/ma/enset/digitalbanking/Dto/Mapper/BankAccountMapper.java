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
    public BankAccountDto toBankAccountDto(BankAccount bankAccount) {
        BankAccountDto bankAccountDto;
        if(bankAccount instanceof CurrentAccount) {
            bankAccountDto = new CurrentAccountDto();
            ((CurrentAccountDto) bankAccountDto).setId(bankAccount.getId());
            ((CurrentAccountDto) bankAccountDto).setBalance(bankAccount.getBalance());
            ((CurrentAccountDto) bankAccountDto).setStatus(bankAccount.getStatus());
            ((CurrentAccountDto) bankAccountDto).setOverDraft(((CurrentAccount) bankAccount).getOverDraft());
            bankAccountDto.setType(CurrentAccount.class.getSimpleName());
        } else {
            bankAccountDto = new SavingAccountDto();
            ((SavingAccountDto) bankAccountDto).setId(bankAccount.getId());
            ((SavingAccountDto) bankAccountDto).setBalance(bankAccount.getBalance());
            ((SavingAccountDto) bankAccountDto).setStatus(bankAccount.getStatus());
            ((SavingAccountDto) bankAccountDto).setInterestRate(((SavingAccount) bankAccount).getInterestRate());
            bankAccountDto.setType(SavingAccount.class.getSimpleName());
        }
        bankAccountDto.setCustomer(customerMapper.toCustomerDto(bankAccount.getCustomer()));
        return bankAccountDto;
    }

    public BankAccount fromBankAccountDto(BankAccountDto bankAccountDto) {
        BankAccount bankAccount;
        if(bankAccountDto.getType().equals(CurrentAccount.class.getSimpleName())) {
            bankAccount = new CurrentAccount();
            bankAccount.setId(((CurrentAccountDto) bankAccountDto).getId());
            bankAccount.setBalance(((CurrentAccountDto) bankAccountDto).getBalance());
            bankAccount.setStatus(((CurrentAccountDto) bankAccountDto).getStatus());
            ((CurrentAccount) bankAccount).setOverDraft(((CurrentAccountDto) bankAccountDto).getOverDraft());
        } else {
            bankAccount = new SavingAccount();
            bankAccount.setId(((SavingAccountDto) bankAccountDto).getId());
            bankAccount.setBalance(((SavingAccountDto) bankAccountDto).getBalance());
            bankAccount.setStatus(((SavingAccountDto) bankAccountDto).getStatus());
            ((SavingAccount) bankAccount).setInterestRate(((SavingAccountDto) bankAccountDto).getInterestRate());
        }
        return bankAccount;
    }

    public List<BankAccountDto> toBankAccountDtos(List<BankAccount> bankAccounts) {
        List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> toBankAccountDto(bankAccount)).collect(Collectors.toList());
        return bankAccountDtos;
    }

    public List<BankAccount> fromBankAccountDtos(List<BankAccountDto> bankAccountDtos) {
        List<BankAccount> bankAccounts = bankAccountDtos.stream().map(bankAccountDto -> fromBankAccountDto(bankAccountDto)).collect(Collectors.toList());
        return bankAccounts;
    }
}
