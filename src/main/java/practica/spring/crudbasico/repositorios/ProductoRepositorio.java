package practica.spring.crudbasico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import practica.spring.crudbasico.modelos.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto,Long> {

}
