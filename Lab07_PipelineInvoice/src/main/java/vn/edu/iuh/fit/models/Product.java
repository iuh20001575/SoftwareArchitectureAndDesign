package vn.edu.iuh.fit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int quantity;
    private String image;
    private double price;

    public Product(String name, String description, int quantity, String image, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
    }
}
