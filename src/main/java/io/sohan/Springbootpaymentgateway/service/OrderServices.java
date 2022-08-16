package io.sohan.Springbootpaymentgateway.service;


import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.request.OrderRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.model.Orders;

import java.util.List;


public interface OrderServices {
    List<OrderResponseDto> getAll(Long customerId);

    OrderResponseDto getById(Long customerId, String id);

    Orders getOrder(String id);

    OrderResponseDto add(OrderRequestDto orderRequestDto);

    //    void update(String id, OrderCheckDto orderCheckDto) throws RazorpayException;
//    Orders update(OrderCheckDto orderCheckDto) throws RazorpayException;

    Orders update(OrderCheckDto orderCheckDto) throws RazorpayException;

    OrderResponseDto pending(String id);

    Boolean delete(Long id);
}
