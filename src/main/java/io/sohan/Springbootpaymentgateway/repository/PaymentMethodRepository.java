package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {


    Optional<PaymentMethod> findByCardIdAndCustomersId(String cardId, Long customerId);

    Optional<PaymentMethod> findByVpaAndCustomersId(String vpa, Long customerId);

    Optional<PaymentMethod> findByCardId(String cardId);

    Optional<PaymentMethod> findByVpa(String vpa);
}
