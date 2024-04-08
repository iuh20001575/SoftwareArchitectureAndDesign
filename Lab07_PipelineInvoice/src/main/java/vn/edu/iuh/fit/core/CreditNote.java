package vn.edu.iuh.fit.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_notes")
@Getter
@NoArgsConstructor
public class CreditNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long creditNote;
    private boolean cancellation;
    private String notes;


    public CreditNote(long creditNote, String notes) {
        this.creditNote = creditNote;
        this.notes = notes;
        this.cancellation = false;
    }

    public void setCreditNote(long creditNote) {
        this.creditNote = creditNote;
    }

    public void setCancellation(boolean cancellation) {
        this.cancellation = cancellation;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CreditNote{" +
                "creditNote=" + creditNote +
                ", cancellation=" + cancellation +
                ", notes='" + notes + '\'' +
                '}';
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
