package vn.edu.iuh.fit.core;

import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;
    private long custId;
    private String cardNumber;
    private String cvv; // Card Verification Value

    public Payment(long paymentId, long custId, String cardNumber, String cvv) {
        this.paymentId = paymentId;
        this.custId = custId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}
