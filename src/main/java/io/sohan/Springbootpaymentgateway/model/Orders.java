package io.sohan.Springbootpaymentgateway.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Orders {

    @Id
    private String id;
    private int amount;
    private int amount_paid;
    private int amount_due;
    private String currency;
    private String receipt;
    private String status;
    //    @Builder.Default
    private String refund_status;// = "Not refund";
    private int attempts;
    private String razorpay_payment_id;
    private String razorpay_signature;
    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;

    @OneToOne(mappedBy = "orders")
    private Payments payments;
    /**
     * It represents record created date.
     */
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    /**
     * It represents record updated date.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
/**
 * private int amount_paid;
 * private int amount_due;
 * private String currency;
 * private String receipt;
 * private String status;
 * private int attempts;
 * private String razorpay_payment_id;
 * private String razorpay_signature;
 */