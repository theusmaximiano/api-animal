package com.example.animal_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

// Filtro de autenticação que valida o token JWT presente no header Authorization das requisições.
// Se o token for válido, autentica o usuário no contexto do Spring Security para permitir acesso aos endpoints protegidos.
// O segredo da assinatura deve ser o mesmo usado para emitir o token na Auth API.

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret = "chave-super-secreta-do-matheus-1234567890"; // mesma chave usada na auth-api

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && validateToken(jwt)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.emptyList() // você pode adicionar roles aqui se quiser
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer "
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}