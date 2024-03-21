package vn.iuh.iuh.fit.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@ToString
@NoArgsConstructor
@Setter
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addr;
    private String city;
    private String country;
    private String postcode;

    public Address(String addr, String city, String country, String postcode) {
        this.addr = addr;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
    }
}
