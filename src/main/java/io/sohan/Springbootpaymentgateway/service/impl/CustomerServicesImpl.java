package io.sohan.Springbootpaymentgateway.service.impl;

import io.sohan.Springbootpaymentgateway.Conveter.CustomerConverter;
import io.sohan.Springbootpaymentgateway.Conveter.OrderConverter;
import io.sohan.Springbootpaymentgateway.dto.request.CustomerRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.repository.CustomerRepository;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServicesImpl implements CustomerServices {
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final OrderConverter orderConverter;

    public CustomerServicesImpl(CustomerRepository customerRepository, CustomerConverter customerConverter, OrderConverter orderConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.orderConverter = orderConverter;
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        List<Customers> customersList = customerRepository.findAll();

        if (customersList.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
//        System.out.println(customersList);
        return customersList.stream()
                .map(customerConverter::customersToCustomerResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto getById(Long id) {
        Optional<Customers> optionalCustomers = customerRepository.findById(id);

        if (optionalCustomers.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        System.out.println(optionalCustomers.get());
        return customerConverter.customersToCustomerResponseDto(optionalCustomers.get());
    }

    @Override
    public Customers getByContact(String contact) {
        Optional<Customers> optionalCustomers = customerRepository.findByContact(contact);
        if (!optionalCustomers.isPresent()) {
            throw new RuntimeException("No data available!!");
        }
        return optionalCustomers.get();

    }

    @Override
    public CustomerRequestDto add(CustomerRequestDto customerRequestDto) {
        if (customerRepository.findByContact(customerRequestDto.getContact()).isPresent()) {
            throw new RuntimeException("Already Exists!");
        }
        Customers customers = customerConverter.customerDtoToCustomers(customerRequestDto);
        System.out.println(customers);
        customers = customerRepository.save(customers);
        System.out.println(customers);
        return customerConverter.customersToCustomerDto(customers);
    }

    @Override
    public CustomerRequestDto update(Long id, CustomerRequestDto customerRequestDto) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Resource doesn't exist!");
        }
//        Optional<Customers> optionalCustomers=customerRepository.findByContact(customerDto.getContact());
//        if (!optionalCustomers.isPresent()) {
//            throw new RuntimeException("No data available!!");
//        }

        Customers customers = customerRepository.getReferenceById(id);
        customers = customerRepository.save(customers);
        return customerConverter.customersToCustomerDto(customers);
    }

    @Override
    public Boolean delete(Long id) {
//        if (customerRepository.findById(id).isEmpty()) {
//            throw new RuntimeException("Resource doesn't exist!");
//        }
//
//        Customers customers = customerRepository.getReferenceById(id);
//        customerRepository.delete(customers);
//        customerRepository.save(customers);
        return true;
    }
}
