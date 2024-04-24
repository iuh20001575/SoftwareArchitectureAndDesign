package vn.edu.iuh.fit.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserCredentialsDTO {
    private String phone;
    private String password;
}
