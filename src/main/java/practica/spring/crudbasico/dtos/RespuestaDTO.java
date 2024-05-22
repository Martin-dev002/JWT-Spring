package practica.spring.crudbasico.dtos;

import lombok.Data;

@Data
public class RespuestaDTO {
    private  int numeroDeErrores;

    private String mensaje;
}
