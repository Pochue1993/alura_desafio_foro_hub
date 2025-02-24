package com.challengehub.forohub.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOCrearUsuario (@NotBlank String username,
                             @Email String email,
                             @NotBlank String password,
                             @NotNull Integer typeOfProfile
                             )
{
}
