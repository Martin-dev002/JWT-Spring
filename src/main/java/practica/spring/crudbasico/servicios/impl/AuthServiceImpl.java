package practica.spring.crudbasico.servicios.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import practica.spring.crudbasico.dtos.LoginDTO;
import practica.spring.crudbasico.dtos.RespuestaDTO;
import practica.spring.crudbasico.modelos.Rol;
import practica.spring.crudbasico.modelos.Usuario;
import practica.spring.crudbasico.repositorios.UsuarioRepositorio;
import practica.spring.crudbasico.servicios.IAuthService;
import practica.spring.crudbasico.servicios.IJWTUtilityService;
import practica.spring.crudbasico.validaciones.ValidacionUsuario;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private ValidacionUsuario validacionUsuario;

    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<Usuario> usuario = usuarioRepositorio.findByEmail(login.getEmail());

            if (usuario.isEmpty()) {
                jwt.put("error", "Usuario no registrado");
                return jwt;
            }

            if (verifyPassword(login.getPassword(), usuario.get().getPassword())) {
                jwt.put("jwt", jwtUtilityService.generateJWT(usuario));
            } else {
                jwt.put("error", "Autenticacion fallida");
            }
            return jwt;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @Override
    public RespuestaDTO registro(Usuario usuario) throws Exception {
        try {
            RespuestaDTO respuesta = validacionUsuario.validar(usuario);
            if (respuesta.getNumeroDeErrores() > 0) {
                return respuesta;
            }

            List<Usuario> getAllUsers = usuarioRepositorio.findAll();


            for (Usuario existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(usuario.getEmail())) {
                    respuesta.setNumeroDeErrores(1);
                    respuesta.setMensaje("El usuario ya existe");
                    return respuesta;
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuario.setRole(Rol.USER);
            usuarioRepositorio.save(usuario);

            respuesta.setMensaje("Usuario creado correctamente");

            return respuesta;

        } catch (Exception e) {
            // Imprime el rastreo de la pila o loguea el mensaje de la excepción
            e.printStackTrace(); // O utiliza tu sistema de registro preferido

            // Lanza una nueva excepción con un mensaje personalizado
            throw new Exception("Error durante el registro: " + e.getMessage());
        }

    }
    ///*
    @Override
    public RespuestaDTO registroAdmin(Usuario usuario) throws Exception {
        try {
            RespuestaDTO respuesta = validacionUsuario.validar(usuario);
            if (respuesta.getNumeroDeErrores() > 0) {
                return respuesta;
            }

            List<Usuario> getAllUsers = usuarioRepositorio.findAll();


            for (Usuario existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(usuario.getEmail())) {
                    respuesta.setNumeroDeErrores(1);
                    respuesta.setMensaje("El usuario ya existe");
                    return respuesta;
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuario.setRole(Rol.ADMIN);
            usuarioRepositorio.save(usuario);

            respuesta.setMensaje("Usuario creado correctamente");

            return respuesta;

        } catch (Exception e) {
            // Imprime el rastreo de la pila o loguea el mensaje de la excepción
            e.printStackTrace(); // O utiliza tu sistema de registro preferido

            // Lanza una nueva excepción con un mensaje personalizado
            throw new Exception("Error durante el registro: " + e.getMessage());
        }

    }
    //*/
    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
