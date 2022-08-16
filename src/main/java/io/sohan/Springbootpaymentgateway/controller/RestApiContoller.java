package io.sohan.Springbootpaymentgateway.controller;

import com.google.gson.Gson;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import io.sohan.Springbootpaymentgateway.service.OrderServices;
import io.sohan.Springbootpaymentgateway.service.impl.PaymentMethodServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class RestApiContoller {
    private final Gson gson = new Gson();
    private OrderServices orderServices;
    private CustomerServices customerServices;
    private PaymentMethodServiceImpl paymentMethodService;

    public RestApiContoller(OrderServices orderServices, CustomerServices customerServices, PaymentMethodServiceImpl paymentMethodService) {
        this.orderServices = orderServices;
        this.customerServices = customerServices;
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("orders/{id}")
    public List<OrderResponseDto> getAllOrder(@PathVariable long id) {
        return orderServices.getAll(id);
    }

    @GetMapping("customers")
    public List<CustomerResponseDto> getAllCustomers() {
        return customerServices.getAll();
    }

    @GetMapping("customers/{phNo}")
    private Customers getByContact(@PathVariable String phNo) {
        return customerServices.getByContact(phNo);
    }

//    @GetMapping
//    public List<Object> getListCard() throws RazorpayException {
//
//        return paymentMethodService.getListCard();
//
//    }
//
//    @GetMapping("card/{id}")
//    public Object getCard(@PathVariable String id) throws RazorpayException {
//
//        return paymentMethodService.getCard(id);
//
//    }
}
