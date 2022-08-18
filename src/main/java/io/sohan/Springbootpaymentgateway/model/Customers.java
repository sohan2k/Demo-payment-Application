package io.sohan.Springbootpaymentgateway.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String contact;
    private String gstin;
    //    @JsonIgnore
    @OneToMany(mappedBy = "customers")
    private List<Orders> orders;

    @OneToMany(mappedBy = "customers")
    private List<PaymentMethod> paymentMethods;

    @OneToMany(mappedBy = "customers")
    private List<Cards> cards;

    /**
     * It represents record created date.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdDate;
    /**
     * It represents record updated date.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
