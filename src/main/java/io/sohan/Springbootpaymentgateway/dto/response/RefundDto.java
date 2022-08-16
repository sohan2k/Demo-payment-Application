package io.sohan.Springbootpaymentgateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundDto {
    private String id;
    private int amount;
    private String currency;
    private String payment_id;
    private String receipt;
    private String batch_id;
    private String status;
    private String speed_processed;
    private String speed_requested;
    private String created_at;
}
