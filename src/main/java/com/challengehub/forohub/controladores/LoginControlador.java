package com.challengehub.forohub.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challengehub.forohub.DTOs.DTOLoginDatosUsuario;
import com.challengehub.forohub.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/login")
public class LoginControlador
{
    @Autowired
    UsuarioServicio usuarioServicio;

    @PostMapping
    @Operation(summary = "Metodo que devuelve un token si las credenciales son correctas", tags = "logueo")
    public ResponseEntity checkAuthentication(@RequestBody DTOLoginDatosUsuario dtoLoginDataUser)
    {
        return ResponseEntity.ok(usuarioServicio.authenticateUser(dtoLoginDataUser));
    }
}
