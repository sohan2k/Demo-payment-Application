package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.Refunds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refunds, String> {
}
