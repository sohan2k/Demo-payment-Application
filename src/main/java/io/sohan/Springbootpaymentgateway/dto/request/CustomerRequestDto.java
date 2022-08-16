package io.sohan.Springbootpaymentgateway.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {
    //    private long id;
    private String name;
    private String email;
    private String contact;
//    private String gstin;
//    @JsonProperty("orders")
//    private List<OrderDto> ordersDtos;
//
//    @JsonProperty("paymentMethods")
//    private List<PaymentMethodDto> paymentMethodsDtos;
//    private Date createdDate;
}
