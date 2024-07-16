package com.challengehub.forohub.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challengehub.forohub.modelos.Perfil;

public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {
}
