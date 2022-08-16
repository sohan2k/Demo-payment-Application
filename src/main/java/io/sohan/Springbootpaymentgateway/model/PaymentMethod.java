package io.sohan.Springbootpaymentgateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String method;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;

}
