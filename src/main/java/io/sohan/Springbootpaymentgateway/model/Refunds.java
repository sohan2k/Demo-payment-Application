package io.sohan.Springbootpaymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Refunds {
    @Id
    private String id;
    private int amount;
    private String currency;
    //    private String payment_id;
    private String receipt;
    private String batch_id;
    private String status;
    private String speed_processed;
    private String speed_requested;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payments payments;
    private String created_at;
}
