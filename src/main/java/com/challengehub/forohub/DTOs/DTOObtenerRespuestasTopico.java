package com.challengehub.forohub.DTOs;

import java.time.LocalDateTime;

public record DTOObtenerRespuestasTopico(
        Integer codeTopic,
        String messageTopic,
        Integer codeResponse,
        String messageResponse,
        String solution,
        LocalDateTime creationDate,
        String usernameAuthor
) {
}
