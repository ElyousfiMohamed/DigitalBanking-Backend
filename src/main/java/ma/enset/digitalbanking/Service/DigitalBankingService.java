package ma.enset.digitalbanking.Service;

import ma.enset.digitalbanking.Dto.*;
import ma.enset.digitalbanking.Exception.BalanceNotSufficientException;
import ma.enset.digitalbanking.Exception.BankAccountNotFoundException;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Exception.OperationNotFoundException;

import java.util.List;

public interface DigitalBankingService {
    CustomerDto getCustomer(String customerId) throws CustomerNotFoundException;
    List<CustomerDto> getCustomers() throws CustomerNotFoundException;

    OperationDto getOperation(Long operationId) throws OperationNotFoundException;

    BankAccountDto getBankAccount(String bankAccountId) throws BankAccountNotFoundException;

    CustomerDto saveCustomer(CustomerDto customerDto);

    BankAccountDto saveBankAccount(double balance, String type, String customerId) throws CustomerNotFoundException;

    void debit(String bankAccountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String bankAccountId, double amount) throws BankAccountNotFoundException;

    void transfer(String bankAccountSrc, String bankAccountDst, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    CustomerDto updateCustomer(CustomerDto customerDto, String id);

    void deleteCustomer(String id);

    List<BankAccountDto> getBankAccounts();

    List<OperationDto> getOperations();

    BankAccountDto updateBankAccount(BankAccountDto bankAccountDto, String id);

    void deleteBankAccount(String id);

}

