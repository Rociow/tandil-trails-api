package tandil_trails.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Esta clase se encargará de generar y validar los tokens JWT para la autenticación de usuarios.
 * Utilizaremos la biblioteca jjwt para manejar los tokens JWT.
 */

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    /*Construye el JWT. Un token tiene tres partes: header (algoritmo), payload (datos) y firma.
    Acá defino el payload con el username como subject, la fecha de emisión y la de expiración.
    Al final .signWith() firma con la clave secreta y .compact() lo convierte en el String: xxxxx.yyyyy.zzzzz.*/
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /*Valida el token JWT. Primero extrae el nombre de usuario del token y lo compara con el nombre de usuario del UserDetails proporcionado.
    Además, verifica que el token no haya expirado. Si ambas condiciones se cumplen, el token se considera válido.*/
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /*Verifica si el token JWT ha expirado. Extrae la fecha de expiración del token y la compara con la fecha actual. Si la fecha de expiración es anterior a la fecha actual, el token se considera expirado.*/
    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    /*Extrae el nombre de usuario del token JWT. Utiliza la clave de firma para verificar el token y luego obtiene el sujeto (nombre de usuario) del payload del token.*/
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /*Toma el secreto definido en application.yml, lo decodifica de Base64 a bytes, y construye una clave criptográfica con ella. Esta clave se usa para firmar y verificar tokens.*/
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
