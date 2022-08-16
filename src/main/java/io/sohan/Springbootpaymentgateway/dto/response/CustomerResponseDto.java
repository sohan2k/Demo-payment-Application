package io.sohan.Springbootpaymentgateway.dto.response;

import io.sohan.Springbootpaymentgateway.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String contact;
    private String gstin;
    private OrderResponseDto orders;
    private PaymentMethod paymentMethod;
    private Date createdDate;
}
