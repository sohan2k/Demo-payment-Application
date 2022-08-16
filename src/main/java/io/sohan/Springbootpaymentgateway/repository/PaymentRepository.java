package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, String> {

    Optional<Payments> findById(String id);
}
