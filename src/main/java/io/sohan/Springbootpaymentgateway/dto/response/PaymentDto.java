package io.sohan.Springbootpaymentgateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String id;
    private int amount;
    private String currency;
    private String status;
    private String order_id;
    private String invoice_id;
    private Boolean international;
    private String method;
    private int amount_refunded;
    private String refund_status;
    private Boolean captured;
    private String description;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String email;
    private String contact;
    private int fee;
    private int tax;
    private String error_code;
    private String error_description;
    private String error_source;
    private String error_step;
    private String error_reason;

}
