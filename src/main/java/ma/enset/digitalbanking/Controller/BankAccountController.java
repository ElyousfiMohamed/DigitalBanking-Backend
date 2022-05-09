package ma.enset.digitalbanking.Controller;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.BankAccountDto;
import ma.enset.digitalbanking.Dto.CustomerDto;
import ma.enset.digitalbanking.Exception.BankAccountNotFoundException;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Service.DigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
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

    @PostMapping("/bankAccounts")
    public BankAccountDto addBankAccount(double balance, String type, String customerId) throws CustomerNotFoundException {
        return digitalBankingService.saveBankAccount(balance,type,customerId);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccountDto updateBankAccount(@RequestBody BankAccountDto BankAccountDto,@PathVariable String id) {
        return digitalBankingService.updateBankAccount(BankAccountDto,id);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteBankAccount(@PathVariable String id) {
        digitalBankingService.deleteBankAccount(id);
    }
}
