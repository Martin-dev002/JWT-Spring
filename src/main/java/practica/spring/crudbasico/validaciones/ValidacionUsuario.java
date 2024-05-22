package practica.spring.crudbasico.validaciones;

import practica.spring.crudbasico.dtos.RespuestaDTO;
import practica.spring.crudbasico.modelos.Usuario;

public class ValidacionUsuario {
    public RespuestaDTO validar (Usuario usuario){
        RespuestaDTO respuesta = new RespuestaDTO();

        respuesta.setNumeroDeErrores(0);

        if (usuario.getPrimerNombre() == null
                || usuario.getPrimerNombre().length() < 3
                || usuario.getPrimerNombre().length() > 15) {
            respuesta.setNumeroDeErrores(respuesta.getNumeroDeErrores()+1);
            respuesta.setMensaje("El primer nombre no puede ser nulo ni ser menor a 3 caracteres o mayor a 15");
        }

        if (usuario.getApellido() == null
                || usuario.getApellido().length() < 3
                || usuario.getApellido().length() > 15) {
            respuesta.setNumeroDeErrores(respuesta.getNumeroDeErrores()+1);
            respuesta.setMensaje("El apellido no puede ser nulo ni ser menor a 3 caracteres o mayor a 15");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            respuesta.setNumeroDeErrores(respuesta.getNumeroDeErrores() + 1);
            respuesta.setMensaje("Formato de email incorrecto");
        }

        if (usuario.getPassword() == null || !usuario.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")){
            respuesta.setNumeroDeErrores(respuesta.getNumeroDeErrores()+1);
            respuesta.setMensaje("Formato de contraseña incorrecto");
        }

        return respuesta;
    }
}
