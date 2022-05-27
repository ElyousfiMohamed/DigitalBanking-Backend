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

    CurrentAccountDto saveCurrentAccount(double balance, double overDraft, String customerId) throws CustomerNotFoundException;

    SavingAccountDto saveSavingAccount(double balance, double interestRate, String customerId) throws CustomerNotFoundException;

    void debit(String bankAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String bankAccountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String bankAccountSrc, String bankAccountDst, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    CustomerDto updateCustomer(CustomerDto customerDto, String id);

    void deleteCustomer(String id);

    List<BankAccountDto> getBankAccounts();

    List<OperationDto> getOperations();

    void deleteBankAccount(String id);

    List<OperationDto> accountHistory(String id);

    AccountHistoryDto accountHistoryPages(String id, int page, int size) throws BankAccountNotFoundException;

    CurrentAccountDto updateCurrentAccount(CurrentAccountDto currentAccountDto);

    SavingAccountDto updateSavingAccount(SavingAccountDto savingAccountDto);

    List<CustomerDto> searchCustomers(String keyword);

    List<BankAccountDto> bankAccountsByCustomerId(String id);

}

