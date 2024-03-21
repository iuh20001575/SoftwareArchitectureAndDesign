package vn.iuh.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iuh.iuh.fit.models.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
}