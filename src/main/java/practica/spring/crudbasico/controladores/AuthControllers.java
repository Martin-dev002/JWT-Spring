package practica.spring.crudbasico.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.spring.crudbasico.dtos.LoginDTO;
import practica.spring.crudbasico.dtos.RespuestaDTO;
import practica.spring.crudbasico.modelos.Usuario;
import practica.spring.crudbasico.servicios.IAuthService;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthControllers {
    @Autowired
    IAuthService authService;

    @PostMapping("/register")
    private ResponseEntity<RespuestaDTO> registro (@RequestBody Usuario usuario) throws Exception {
       return new ResponseEntity<>(authService.registro(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    private ResponseEntity<RespuestaDTO> registroAdmin (@RequestBody Usuario usuario) throws Exception {
        return new ResponseEntity<>(authService.registroAdmin(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private  ResponseEntity<HashMap<String,String>> login (@RequestBody LoginDTO loginRequest) throws Exception {
        HashMap<String,String> login = authService.login(loginRequest);
        if (login.containsKey("jwt"))
            return new ResponseEntity<>(login, HttpStatus.OK);
        return new ResponseEntity<>(login,HttpStatus.UNAUTHORIZED);

    }
}
