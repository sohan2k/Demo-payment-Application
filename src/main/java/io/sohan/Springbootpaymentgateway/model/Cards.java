package io.sohan.Springbootpaymentgateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Cards {
    @Id
    String id;
    String entity;
    String name;
    int last4;
    String network;
    String type;
    String issuer;
    String emi;
    String international;
    String sub_type;
    String token_iin;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;
}
