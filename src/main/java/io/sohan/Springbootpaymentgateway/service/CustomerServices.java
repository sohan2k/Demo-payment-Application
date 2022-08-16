package io.sohan.Springbootpaymentgateway.service;

import io.sohan.Springbootpaymentgateway.dto.request.CustomerRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.model.Customers;

import java.util.List;

public interface CustomerServices {
    List<CustomerResponseDto> getAll();

    CustomerResponseDto getById(Long id);

    Customers getByContact(String contact);

    CustomerRequestDto add(CustomerRequestDto customerRequestDto);

    CustomerRequestDto update(Long id, CustomerRequestDto customerRequestDto);

    Boolean delete(Long id);
}
