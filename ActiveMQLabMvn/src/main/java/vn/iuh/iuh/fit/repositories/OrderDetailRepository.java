package vn.iuh.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iuh.iuh.fit.ids.OrderDetailId;
import vn.iuh.iuh.fit.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}