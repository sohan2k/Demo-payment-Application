package io.sohan.Springbootpaymentgateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
    private Long id;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String method;
    private CustomerResponseDto customerResponseDto;
}
