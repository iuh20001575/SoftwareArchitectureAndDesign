package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.core.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}