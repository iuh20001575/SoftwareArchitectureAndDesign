package vn.edu.iuh.fit.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "invoices")
@Getter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;
    private long custId;
    private long productId;
    private float quantity;
    private String description;

    public Invoice(long custId, long productId, float quantity, String description) {
        this.custId = custId;
        this.productId = productId;
        this.quantity = quantity;
        this.description = description;
    }

    public Invoice(long invoiceId, long custId, long productId, long quantity, String description) {
        this.invoiceId = invoiceId;
        this.custId = custId;
        this.productId = productId;
        this.quantity = quantity;
        this.description = description;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", custId=" + custId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
