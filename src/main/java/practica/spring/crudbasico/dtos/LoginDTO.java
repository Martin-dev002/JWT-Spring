package practica.spring.crudbasico.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
