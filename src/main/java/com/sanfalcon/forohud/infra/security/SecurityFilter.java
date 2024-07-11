package com.sanfalcon.forohud.infra.security;

import com.sanfalcon.forohud.domain.usuario.UsuarioRepository;
import com.sanfalcon.forohud.infra.security.jwt.JwtUtilsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//? Usamos este filtro para validar los tokens
//? Automaticamente se ejecuta este filtro, solo heredamos de "OncePerRequestFilter"
//? Los filtros se ejecutan antes que lleguen a nuestros token
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtilsService jwtUtilsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");
        if (token != null) {
            token = token.replace("Bearer ", "");
            var nombreDeUsuario = jwtUtilsService.getSubject(token);
            if (nombreDeUsuario != null) {
                //? Token valido
                var usuario = usuarioRepository.findByCorreo(nombreDeUsuario);
                //? Forzamos el inicio de session
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
