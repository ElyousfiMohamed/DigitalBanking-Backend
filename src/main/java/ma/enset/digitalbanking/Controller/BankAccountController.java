package ma.enset.digitalbanking.Controller;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.*;
import ma.enset.digitalbanking.Exception.BankAccountNotFoundException;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Model.SavingAccount;
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
}
