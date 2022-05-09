package ma.enset.digitalbanking.Service;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.*;
import ma.enset.digitalbanking.Dto.Mapper.*;
import ma.enset.digitalbanking.Exception.*;
import ma.enset.digitalbanking.Model.*;
import ma.enset.digitalbanking.Repository.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        return bankAccountMapper.toBankAccountDto(bankAccountRepository
                .findById(bankAccountId).orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found")));
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        customerDto.setId(UUID.randomUUID().toString());
        Customer customer = customerMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerDto(savedCustomer);
    }

    @Override
    public BankAccountDto saveBankAccount(double balance, String type, String customerId) throws CustomerNotFoundException {
        BankAccount bankAccount;
        if (type.equals(CurrentAccount.class.getSimpleName())) {
            bankAccount = new CurrentAccount();
        } else {
            bankAccount = new SavingAccount();
        }
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(balance);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customerMapper.fromCustomerDto(getCustomer(customerId)));
        BankAccount saved = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toBankAccountDto(saved);
    }

    @Override
    public void debit(String bankAccountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccountDto bankAccountDto = getBankAccount(bankAccountId);
        BankAccount bankAccount = bankAccountMapper.fromBankAccountDto(bankAccountDto);

        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        else {
            Operation operation = new Operation();
            operation.setOperationType(OperationType.DEBIT);
            operation.setDate(new Date());
            operation.setAmount(amount);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
            operation.setBankAccount(bankAccount);
            operationRepository.save(operation);
        }
    }

    @Override
    public void credit(String bankAccountId, double amount) throws BankAccountNotFoundException {
        BankAccountDto bankAccountDto = getBankAccount(bankAccountId);
        BankAccount bankAccount = bankAccountMapper.fromBankAccountDto(bankAccountDto);
        Operation operation = new Operation();
        operation.setOperationType(OperationType.CREDIT);
        operation.setDate(new Date());
        operation.setAmount(amount);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
    }

    @Override
    public void transfer(String bankAccountSrc, String bankAccountDst, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(bankAccountSrc, amount);
        credit(bankAccountDst, amount);
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
        return bankAccountMapper.toBankAccountDtos(bankAccountRepository.findAll());
    }

    @Override
    public List<OperationDto> getOperations() {
        return operationMapper.toOperationDtos(operationRepository.findAll());
    }

    @Override
    public BankAccountDto updateBankAccount(BankAccountDto bankAccountDto, String id) {
        BankAccount bankAccount = bankAccountMapper.fromBankAccountDto(bankAccountDto);
        bankAccount.setId(id);
        BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toBankAccountDto(bankAccountSaved);
    }

    @Override
    public void deleteBankAccount(String id) {
        bankAccountRepository.deleteById(id);
    }
}
