package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, String> {

    List<Orders> findAllByCustomersId(Long customerId);

    Optional<Orders> findByCustomersIdAndId(Long customerId, String id);

    Optional<Orders> findByPaymentsIdAndId(String payId, String id);

}
