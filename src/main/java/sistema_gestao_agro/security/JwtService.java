package sistema_gestao_agro.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "chave-secreta-super-segura-para-jwt-sistema-agro";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
  
    // método para gerar um token JWT com o email e a role do usuário
  public String generateToken(String email, String role) {
    
    // Gera um token JWT com o email como assunto e a role como uma reivindicação personalizada
    return Jwts.builder()
            .setSubject(email)
            .claim("role", "ROLE_" + role) // Adiciona o papel do usuário como uma reivindicação personalizada
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}

// método para validar um token JWT
public boolean isTokenValid(String token) {
    
    // Tenta analisar o token usando a chave secreta. Se o token for inválido ou expirado, uma exceção será lançada.
    try {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return true;

    } catch (JwtException e) {
        return false;
    }
}
    // método para extrair o email do token JWT
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}