package practica.spring.crudbasico.modelos;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    Long id;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "apellido")
    private String apellido;

    private String email;

    private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Rol role;
}
