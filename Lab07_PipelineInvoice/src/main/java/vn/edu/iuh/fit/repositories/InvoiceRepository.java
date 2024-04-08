package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.core.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}