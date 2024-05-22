package practica.spring.crudbasico.dtos;

import lombok.*;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
}
