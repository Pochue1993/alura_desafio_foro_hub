package com.challengehub.forohub.DTOs;

import com.challengehub.forohub.modelos.Topico;
import com.challengehub.forohub.modelos.Usuario;

public record DTOGuardarRespuesta(
        String message,
        Topico topic,
        Usuario author,
        String solution
) {
}
