package vn.iuh.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iuh.iuh.fit.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}