package vn.iuh.iuh.fit.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_quantities")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(unique = true)
    private Product product;
    private float quantity;

    public ProductQuantity(Product product, float quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
