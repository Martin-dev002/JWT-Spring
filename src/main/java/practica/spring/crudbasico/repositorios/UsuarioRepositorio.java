package practica.spring.crudbasico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practica.spring.crudbasico.modelos.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    @Query(value = "SELECT * FROM usuario WHERE email = :email", nativeQuery = true)
    Optional<Usuario> findByEmail(String email);
}
