package practica.spring.crudbasico.controladores;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRolControlers {
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/publico")
    private String publico(){
        return "Publico";
    }
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/privado")
    private String privado(){
        return "Privado";
    }
}
