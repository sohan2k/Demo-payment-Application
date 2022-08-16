package io.sohan.Springbootpaymentgateway.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private String amount;
    private String name;
    private String email;
    private String contact;
    private String Currency;
}
