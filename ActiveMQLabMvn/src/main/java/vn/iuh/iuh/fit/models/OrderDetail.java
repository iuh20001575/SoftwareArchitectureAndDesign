package vn.iuh.iuh.fit.models;

import jakarta.persistence.*;
import lombok.*;
import vn.iuh.iuh.fit.ids.OrderDetailId;

@Entity
@Table(name = "order_details")
@IdClass(OrderDetailId.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @ManyToOne
    private Order order;
    @Id
    @ManyToOne
    private Product product;
    private Float quantity;
}
