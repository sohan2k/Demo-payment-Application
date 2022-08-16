package io.sohan.Springbootpaymentgateway.Conveter;

import io.sohan.Springbootpaymentgateway.dto.request.RefundRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.RefundDto;
import io.sohan.Springbootpaymentgateway.model.Refunds;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RefundConveter {
    private final ModelMapper modelMapper;

    public RefundConveter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RefundDto refundsToRefundDto(Refunds refunds) {
        return modelMapper.map(refunds, RefundDto.class);
    }


    public Refunds refundDtoToRefunds(RefundDto refundDto) {
        return modelMapper.map(refundDto, Refunds.class);
    }

    public Refunds refundRequestDtoToRefunds(RefundRequestDto refundRequestDto) {
        return modelMapper.map(refundRequestDto, Refunds.class);
    }
}
