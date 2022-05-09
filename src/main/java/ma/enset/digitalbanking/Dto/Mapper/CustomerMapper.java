package ma.enset.digitalbanking.Dto.Mapper;

import ma.enset.digitalbanking.Dto.CustomerDto;
import ma.enset.digitalbanking.Model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public CustomerDto toCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }

    public List<CustomerDto> toCustomerDtos(List<Customer> customers) {
        List<CustomerDto> customerDtos = customers.stream().map(customer -> toCustomerDto(customer)).collect(Collectors.toList());
        return customerDtos;
    }

    public Customer fromCustomerDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public List<Customer> fromCustomerDtos(List<CustomerDto> customerDtos) {
        List<Customer> customers = customerDtos.stream().map(customerDto -> fromCustomerDto(customerDto)).collect(Collectors.toList());
        return customers;
    }
}
