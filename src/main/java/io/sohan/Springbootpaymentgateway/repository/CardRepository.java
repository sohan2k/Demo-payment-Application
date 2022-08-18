package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Cards, String> {

    Optional<Cards> findByIdAndCustomersId(String id, Long custId);
}
