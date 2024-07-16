package com.challengehub.forohub.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challengehub.forohub.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>
{
    Usuario findByUsername(String username);
}