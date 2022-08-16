package io.sohan.Springbootpaymentgateway.Conveter;

import io.sohan.Springbootpaymentgateway.dto.response.PaymentDto;
import io.sohan.Springbootpaymentgateway.model.Payments;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    private final ModelMapper modelMapper;

    public PaymentConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentDto paymentsToPaymentDto(Payments payments) {
        return modelMapper.map(payments, PaymentDto.class);
    }


    public Payments paymentDtoToPayments(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payments.class);
    }

    public Payments toUpdatePayments(PaymentDto paymentDto, Payments payments) {
        modelMapper.map(paymentDto, payments);
        return payments;
    }
}
