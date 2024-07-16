package com.challengehub.forohub.DTOs;

import com.challengehub.forohub.modelos.Perfil;

public record DTOGuardarUsuario (String username,
                                       String email,
                                       String passwordEncrypted,
                                       Perfil typeOfProfile){
}
