package io.sohan.Springbootpaymentgateway.Conveter;

import io.sohan.Springbootpaymentgateway.dto.request.OrderRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.model.Orders;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    private final ModelMapper modelMapper;

    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderRequestDto ordersToOrderDto(Orders orders) {
        return modelMapper.map(orders, OrderRequestDto.class);
    }


    public Orders orderDtoToOrders(OrderRequestDto orderRequestDto) {
        return modelMapper.map(orderRequestDto, Orders.class);
    }

    public OrderResponseDto ordersToOrderResponseDto(Orders orders) {
        return modelMapper.map(orders, OrderResponseDto.class);
    }


//    public Orders orderResponseDtoToOrders(OrderRequestDto orderRequestDto) {
//        return modelMapper.map(orderRequestDto, Orders.class);
//    }
}
