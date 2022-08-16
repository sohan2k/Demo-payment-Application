package io.sohan.Springbootpaymentgateway.controller;

import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import io.sohan.Springbootpaymentgateway.service.OrderServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class RestApiContoller {
    private OrderServices orderServices;
    private CustomerServices customerServices;

    public RestApiContoller(OrderServices orderServices, CustomerServices customerServices) {
        this.orderServices = orderServices;
        this.customerServices = customerServices;
    }

    @GetMapping("orders/{id}")
    public List<OrderResponseDto> getAllOrder(@PathVariable long id) {
        return orderServices.getAll(id);
    }

    @GetMapping("customers")
    public List<CustomerResponseDto> getAllCustomers() {
        return customerServices.getAll();
    }
}
