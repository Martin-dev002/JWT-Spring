package practica.spring.crudbasico.servicios;

import practica.spring.crudbasico.dtos.LoginDTO;
import practica.spring.crudbasico.dtos.RespuestaDTO;
import practica.spring.crudbasico.modelos.Usuario;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String, String> login (LoginDTO login) throws Exception;
    public RespuestaDTO registro(Usuario usuario) throws Exception;
    public RespuestaDTO registroAdmin(Usuario usuario) throws Exception;
}
