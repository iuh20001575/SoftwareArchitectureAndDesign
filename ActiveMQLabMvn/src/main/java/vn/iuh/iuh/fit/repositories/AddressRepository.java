package vn.iuh.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iuh.iuh.fit.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}