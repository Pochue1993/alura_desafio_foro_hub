package com.challengehub.forohub.DTOs;

import java.time.LocalDateTime;

public record DTOObtenerTopico (
        LocalDateTime creationDate,
        String message,
        String solution,
        DTOUsuario author
)
{
}
