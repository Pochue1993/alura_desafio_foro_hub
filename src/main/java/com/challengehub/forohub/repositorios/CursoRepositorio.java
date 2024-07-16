package com.challengehub.forohub.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challengehub.forohub.modelos.Curso;

public interface CursoRepositorio extends JpaRepository<Curso, Long>
{
}
