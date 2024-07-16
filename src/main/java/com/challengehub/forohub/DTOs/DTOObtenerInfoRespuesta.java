package com.challengehub.forohub.DTOs;

import java.time.LocalDateTime;

public record DTOObtenerInfoRespuesta
        (Integer codeResponse,
         Integer codeTopic,
         String message,
         String solution,
         LocalDateTime creationDate,
         String usernameAuthor
         )
{
}
