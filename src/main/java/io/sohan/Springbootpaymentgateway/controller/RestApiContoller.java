package io.sohan.Springbootpaymentgateway.controller;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.dto.response.PaymentDto;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import io.sohan.Springbootpaymentgateway.service.OrderServices;
import io.sohan.Springbootpaymentgateway.service.PaymentServices;
import io.sohan.Springbootpaymentgateway.service.impl.CardServiceImpl;
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
    private PaymentServices paymentServices;
    private CardServiceImpl cardService;

    public RestApiContoller(OrderServices orderServices, CustomerServices customerServices, PaymentMethodServiceImpl paymentMethodService, CardServiceImpl cardService) {
        this.orderServices = orderServices;
        this.customerServices = customerServices;
        this.paymentMethodService = paymentMethodService;
        this.cardService = cardService;
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

    @GetMapping("payments/{payId}")
    private PaymentDto getPaymentById(@PathVariable String payId) throws RazorpayException {
        System.out.println(payId);
        return paymentServices.getById(payId);
    }

    @GetMapping("card/{id}")
    public Object getCard(@PathVariable String id) throws RazorpayException {
        return cardService.getCard(id);

    }
}
