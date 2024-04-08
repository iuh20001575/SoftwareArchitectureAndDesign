package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.core.CreditNote;

public interface CreditNoteRepository extends JpaRepository<CreditNote, Long> {
}