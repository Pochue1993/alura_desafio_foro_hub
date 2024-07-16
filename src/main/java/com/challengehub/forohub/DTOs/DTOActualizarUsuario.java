package com.challengehub.forohub.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOActualizarUsuario(@NotBlank String username,
                            @Email String email,
                            @NotBlank String password,
                            @NotBlank Integer typeOfProfile) {
}