package io.sohan.Springbootpaymentgateway.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDto {
    private String amount;
    private String speed;
    private String id;
}
