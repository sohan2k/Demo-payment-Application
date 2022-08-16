package io.sohan.Springbootpaymentgateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String id;
    private int amount;
    private int amount_paid;
    private int amount_due;
    private String currency;
    private String receipt;
    private String status;
    private String refund_status;
    private int attempts;
    private String razorpay_payment_id;
    private String razorpay_signature;
}
