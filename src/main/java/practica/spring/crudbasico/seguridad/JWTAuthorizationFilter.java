package practica.spring.crudbasico.seguridad;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import practica.spring.crudbasico.modelos.Rol;
import practica.spring.crudbasico.servicios.IJWTUtilityService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    IJWTUtilityService jwtUtilityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);

        try {
            JWTClaimsSet claimsSet = jwtUtilityService.parseJWT(token);
            Rol rol = Rol.valueOf(claimsSet.getStringClaim("rol"));  // Extrae el rol del JWT y lo convierte a un enum Rol.
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));  // Crea una lista de autoridades a partir del rol.

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claimsSet.getSubject(),
                    null,
                    authorities);  // Incluye las autoridades en el token de autenticación.

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);
    }
}

