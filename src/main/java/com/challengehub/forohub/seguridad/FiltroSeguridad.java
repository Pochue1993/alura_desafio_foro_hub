package com.challengehub.forohub.seguridad;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.challengehub.forohub.repositorios.UsuarioRepositorio;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroSeguridad extends OncePerRequestFilter {

    @Autowired
    private TokenServicio tokenService;

    @Autowired
    private UsuarioRepositorio usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Get the Token
        String authHeader = request.getHeader("Authorization");
        String subject = "";

        if(authHeader!= null)
        {
            var token =  authHeader.replace("Bearer ", "");

            System.out.println(token);

            subject = tokenService.getSubject(token);

            System.out.println(tokenService.getSubject(token));

            if(subject != null)
            {
                var user = usersRepository.findByUsername(subject);
                var authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
