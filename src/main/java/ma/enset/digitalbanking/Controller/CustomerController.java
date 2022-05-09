package ma.enset.digitalbanking.Controller;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.Dto.CustomerDto;
import ma.enset.digitalbanking.Exception.CustomerNotFoundException;
import ma.enset.digitalbanking.Service.DigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
public class CustomerController {
    private DigitalBankingService digitalBankingService;

    @GetMapping("/customers")
    public List<CustomerDto> customers() throws CustomerNotFoundException {
        return digitalBankingService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto customer(@PathVariable String id) throws CustomerNotFoundException {
        return digitalBankingService.getCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return digitalBankingService.saveCustomer(customerDto);
    }

    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto,@PathVariable String id) {
        return digitalBankingService.updateCustomer(customerDto,id);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id) {
        digitalBankingService.deleteCustomer(id);
    }
}
