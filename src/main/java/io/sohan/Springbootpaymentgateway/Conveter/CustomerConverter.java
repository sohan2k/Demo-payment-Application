package io.sohan.Springbootpaymentgateway.Conveter;

import io.sohan.Springbootpaymentgateway.dto.request.CustomerRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.model.Customers;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    private final ModelMapper modelMapper;

    public CustomerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerRequestDto customersToCustomerDto(Customers customers) {
        return modelMapper.map(customers, CustomerRequestDto.class);
    }


    public Customers customerDtoToCustomers(CustomerRequestDto customerRequestDto) {
        return modelMapper.map(customerRequestDto, Customers.class);
    }

    public CustomerResponseDto customersToCustomerResponseDto(Customers customers) {
        return modelMapper.map(customers, CustomerResponseDto.class);
    }

    public Customers CustomerResponseDtoToCustomers(CustomerResponseDto customerResponseDto) {
        return modelMapper.map(customerResponseDto, Customers.class);
    }
}
