package com.challengehub.forohub.DTOs;

import com.challengehub.forohub.modelos.Curso;
import com.challengehub.forohub.modelos.Estado;
import com.challengehub.forohub.modelos.Usuario;

public record DTOGuardarTopico (
        String title,
        String message,
        Usuario user,
        Curso course,
        Estado status
)
{
}
