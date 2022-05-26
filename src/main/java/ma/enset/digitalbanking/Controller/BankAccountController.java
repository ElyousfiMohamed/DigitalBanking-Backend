package ma.enset.digitalbanking.Controller;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.*;
import ma.enset.digitalbanking.Exception.BalanceNotSufficientException;
import ma.enset.digitalbanking.Exception.BankAccountNotFoundException;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Service.DigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountController {
    private DigitalBankingService digitalBankingService;

    @GetMapping("/bankAccounts")
    public List<BankAccountDto> bankAccounts(){
        return digitalBankingService.getBankAccounts();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccountDto bankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return digitalBankingService.getBankAccount(id);
    }

    @GetMapping("/bankAccounts/{id}/operations")
    public List<OperationDto> bankAccountHistory(@PathVariable String id) {
        return digitalBankingService.accountHistory(id);
    }

    @GetMapping("/bankAccounts/{id}/pgOperations")
    public AccountHistoryDto bankAccountHistory(@PathVariable String id,
                                                @RequestParam(name = "page",defaultValue = "0") int page,
                                                @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return digitalBankingService.accountHistoryPages(id,page,size);
    }

    @PostMapping("/bankAccounts/currentAccount")
    public CurrentAccountDto addCurrentAccount(double balance, double overDraft, String customerId) throws CustomerNotFoundException {
        return digitalBankingService.saveCurrentAccount(balance,overDraft,customerId);
    }

    @PostMapping("/bankAccounts/savingAccount")
    public SavingAccountDto addSavingAccount(double balance, double interestRate, String customerId) throws CustomerNotFoundException {
        return digitalBankingService.saveSavingAccount(balance,interestRate,customerId);
    }


    /*@PutMapping("/bankAccounts/currentAccount/{id}")
    public CurrentAccountDto updateCurrentAccount(@RequestBody CurrentAccountDto currentAccountDto,
                                                  @PathVariable String id) {
        currentAccountDto.setType(id);
        System.out.println(currentAccountDto.getBalance()+" "+currentAccountDto.getOverDraft());
        return digitalBankingService.updateCurrentAccount(currentAccountDto);
    }

    @PutMapping("/bankAccounts/savingAccount/{id}")
    public SavingAccountDto updateSavingAccount(@RequestBody SavingAccountDto savingAccountDto,
                                                @PathVariable String id) {
        savingAccountDto.setId(id);
        System.out.println(savingAccountDto.getBalance()+" "+savingAccountDto.getInterestRate());
        return digitalBankingService.updateSavingAccount(savingAccountDto);
    }*/

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteBankAccount(@PathVariable String id) {
        digitalBankingService.deleteBankAccount(id);
    }

    @RequestMapping(value = "/bankAccounts/debit",method = RequestMethod.POST)
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.digitalBankingService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @RequestMapping(value = "/bankAccounts/credit",method = RequestMethod.POST)
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.digitalBankingService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @RequestMapping(value = "/bankAccounts/transfer",method = RequestMethod.POST)
    public void transfer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        System.out.println("YOOOOO");
        this.digitalBankingService.transfer(
                transferDTO.getAccountSource(),
                transferDTO.getAccountDestination(),
                transferDTO.getAmount(),
                transferDTO.getDescription());
    }
}
