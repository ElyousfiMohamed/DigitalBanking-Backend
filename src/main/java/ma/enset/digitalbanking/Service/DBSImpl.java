package ma.enset.digitalbanking.Service;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.*;
import ma.enset.digitalbanking.Dto.Mapper.*;
import ma.enset.digitalbanking.Exception.*;
import ma.enset.digitalbanking.Model.*;
import ma.enset.digitalbanking.Repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DBSImpl implements DigitalBankingService {
    private CustomerRepository customerRepository;
    private OperationRepository operationRepository;
    private BankAccountRepository bankAccountRepository;
    private CustomerMapper customerMapper;
    private OperationMapper operationMapper;
    private BankAccountMapper bankAccountMapper;

    @Override
    public CustomerDto getCustomer(String customerId) throws CustomerNotFoundException {
        return customerMapper.toCustomerDto(customerRepository
                .findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found")));
    }

    @Override
    public List<CustomerDto> getCustomers() throws CustomerNotFoundException {
        return customerMapper.toCustomerDtos(customerRepository.findAll());
    }

    @Override
    public OperationDto getOperation(Long operationId) throws OperationNotFoundException {
        return operationMapper.toOperationDto(operationRepository
                .findById(operationId).orElseThrow(() -> new OperationNotFoundException("Operation not found")));
    }

    @Override
    public BankAccountDto getBankAccount(String bankAccountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.toCurrentBankAccountDto(currentAccount);
        } else{
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return bankAccountMapper.toSavingBankAccountDto(savingAccount);
        }
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        customerDto.setId(UUID.randomUUID().toString());
        Customer customer = customerMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerDto(savedCustomer);
    }

    @Override
    public CurrentAccountDto saveCurrentAccount(double balance, double overDraft, String customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(balance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        BankAccount bankAccount = currentAccount;
        BankAccount saved = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toCurrentBankAccountDto((CurrentAccount) saved);
    }

    @Override
    public SavingAccountDto saveSavingAccount(double balance, double interestRate, String customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(balance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        BankAccount bankAccount = savingAccount;
        BankAccount saved = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toSavingBankAccountDto((SavingAccount) saved);
    }

    @Override
    public void debit(String bankAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account not found"));
        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        else {
            Operation operation = new Operation();
            operation.setOperationType(OperationType.DEBIT);
            operation.setDate(new Date());
            operation.setAmount(amount);
            operation.setDesciption(description);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
            operation.setBankAccount(bankAccount);
            operationRepository.save(operation);
        }
    }

    @Override
    public void credit(String bankAccountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account not found"));
        Operation operation = new Operation();
        operation.setOperationType(OperationType.CREDIT);
        operation.setDate(new Date());
        operation.setAmount(amount);
        operation.setDesciption(description);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
    }

    @Override
    public void transfer(String bankAccountSrc, String bankAccountDst, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(bankAccountSrc, amount,description);
        credit(bankAccountDst, amount,description);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, String id) {
        customerDto.setId(id);
        Customer customer = customerMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<BankAccountDto> getBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.toCurrentBankAccountDto(currentAccount);
            } else{
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.toSavingBankAccountDto(savingAccount);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> getOperations() {
        return operationMapper.toOperationDtos(operationRepository.findAll());
    }

    @Override
    public void deleteBankAccount(String id) {
        bankAccountRepository.deleteById(id);
    }

    @Override
    public List<OperationDto> accountHistory(String id) {
        List<Operation> bankAccountHistory = operationRepository.findByBankAccountId(id);
        return operationMapper.toOperationDtos(bankAccountHistory);
    }

    @Override
    public AccountHistoryDto accountHistoryPages(String id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account not found"));
        Page<Operation> operations = operationRepository.findByBankAccountId(id, PageRequest.of(page,size));
        List<OperationDto> operationsdto = operationMapper.toOperationDtos(operations.getContent());
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        accountHistoryDto.setOperationDtos(operationsdto);
        accountHistoryDto.setId(id);
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(operations.getTotalPages());
        return accountHistoryDto;
    }

    @Override
    public CurrentAccountDto updateCurrentAccount(CurrentAccountDto currentAccountDto) {
        CurrentAccount currentAccount = bankAccountMapper.fromCurrentBankAccountDto(currentAccountDto);
        CurrentAccount saved = bankAccountRepository.save(currentAccount);
        return bankAccountMapper.toCurrentBankAccountDto(saved);
    }

    @Override
    public SavingAccountDto updateSavingAccount(SavingAccountDto savingAccountDto) {
        SavingAccount savingAccount = bankAccountMapper.fromSavingBankAccountDto(savingAccountDto);
        SavingAccount saved = bankAccountRepository.save(savingAccount);
        return bankAccountMapper.toSavingBankAccountDto(saved);
    }


}
