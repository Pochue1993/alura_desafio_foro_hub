package com.challengehub.forohub.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public record DTOObtenerRegistrosTopico(Integer id,
                                      String title,
                                      String message,
                                      LocalDateTime creationDate,
                                      String status,
                                      DTOUsuario user,
                                      String course,
                                      List<DTOObtenerTopico> listResponses
                               )
{

}
