package io.sohan.Springbootpaymentgateway.service;

import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.request.RefundRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.RefundDto;

import java.util.List;

public interface RefundServices {
    List<RefundDto> getAll(Long paymentId);

    RefundDto add(RefundRequestDto refundRequestDto) throws RazorpayException;
}
