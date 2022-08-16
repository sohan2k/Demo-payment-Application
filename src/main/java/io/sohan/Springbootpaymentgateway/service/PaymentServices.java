package io.sohan.Springbootpaymentgateway.service;

import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.response.PaymentDto;
import io.sohan.Springbootpaymentgateway.model.Payments;

import java.util.List;

public interface PaymentServices {
    List<PaymentDto> getAll(Long customerId);

    PaymentDto getById(String id) throws RazorpayException;

    Payments add(OrderCheckDto orderCheckDto) throws RazorpayException;

    //    PaymentDto add(OrderCheckDto orderCheckDto, Orders orders) throws RazorpayException;
    PaymentDto update(String paymentId) throws RazorpayException;

    Boolean delete(Long id);
}
