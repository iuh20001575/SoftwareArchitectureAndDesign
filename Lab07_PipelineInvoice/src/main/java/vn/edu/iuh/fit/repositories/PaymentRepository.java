package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.core.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}