package practica.spring.crudbasico.servicios.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import practica.spring.crudbasico.modelos.Usuario;
import practica.spring.crudbasico.servicios.IJWTUtilityService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTUtilityServicesImpl implements IJWTUtilityService {
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;

    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;
    public String generateJWT(Optional<Usuario> usuario) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);
        JWSSigner signer = new RSASSASigner(privateKey);
        Date now = new Date();
        System.out.println(usuario.get().getRole().name().trim());
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(usuario.get().getId().toString())
                .claim("rol", usuario.get().getRole().name().trim())  // Añade el rol del usuario al JWT.
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + 14400000))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
    @Override
    public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey = loadPublicKey(publicKeyResource);
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

        if (!signedJWT.verify(verifier)) { //token no verificado
            throw  new JOSEException("Firma Invalida");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Token Expirado");
        }
        return claimsSet;
    }
    private PrivateKey loadPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s", "");

        byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
    }
    private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s", "");

        byte[] decodeKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    }
}
