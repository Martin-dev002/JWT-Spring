package practica.spring.crudbasico.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreacionFallida extends  RuntimeException{
    public CreacionFallida(String mensaje){ super(mensaje); }
}
