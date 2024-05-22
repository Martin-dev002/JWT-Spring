package practica.spring.crudbasico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import practica.spring.crudbasico.validaciones.ValidacionUsuario;

@Configuration
public class ValidacionesConfig {
    @Bean
    public ValidacionUsuario validacionUsuario(){
        return new ValidacionUsuario();
    }
}
