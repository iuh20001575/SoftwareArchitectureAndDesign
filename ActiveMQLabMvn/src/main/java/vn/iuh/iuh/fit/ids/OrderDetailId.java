package vn.iuh.iuh.fit.ids;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailId implements Serializable {
    private Long order;
    private Long product;
}
