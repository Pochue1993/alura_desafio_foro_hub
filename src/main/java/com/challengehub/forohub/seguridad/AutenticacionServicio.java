package com.challengehub.forohub.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challengehub.forohub.modelos.Usuario;
import com.challengehub.forohub.repositorios.UsuarioRepositorio;

@Service
public class AutenticacionServicio  implements UserDetailsService
{
    @Autowired
    UsuarioRepositorio usersRepository;

    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository.findByUsername(username);
    }
}
