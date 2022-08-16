package io.sohan.Springbootpaymentgateway.repository;

import io.sohan.Springbootpaymentgateway.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findById(Long id);

    Optional<Customers> findByContact(String Contact);

    Customers getByContact(String s);
}
