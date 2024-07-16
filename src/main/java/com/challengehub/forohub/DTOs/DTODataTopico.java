package com.challengehub.forohub.DTOs;

import java.time.LocalDateTime;

import com.challengehub.forohub.modelos.Curso;
import com.challengehub.forohub.modelos.Estado;
import com.challengehub.forohub.modelos.Usuario;

public record DTODataTopico (
        String title,
        String message,
        LocalDateTime creationdate,
        Estado status,
        Usuario author,
        Curso course
)
{
}
