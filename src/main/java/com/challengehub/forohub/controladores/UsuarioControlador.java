package com.challengehub.forohub.controladores;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challengehub.forohub.DTOs.DTOCrearUsuario;
import com.challengehub.forohub.DTOs.DTOActualizarUsuario;
import com.challengehub.forohub.DTOs.DTODetalleUsuarios;
import com.challengehub.forohub.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UsuarioControlador
{
    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping
    @Operation(summary = "Obtener usuarios", tags = "Get")
    public ResponseEntity obtenerUsuarios()
    {
        List<DTODetalleUsuarios> listaDetalles = usuarioServicio.findAllUsers();

        return ResponseEntity.ok(listaDetalles);
    }

    @GetMapping("{id}")
    @Operation(summary = "Obtener usuario", tags = "Get")
    public ResponseEntity<DTODetalleUsuarios> findUserById(@PathVariable Integer id)
    {
        DTODetalleUsuarios Detalles = usuarioServicio.findUserById(Long.valueOf(id));

        return ResponseEntity.ok(Detalles);
    }

    @PostMapping
    @Operation(summary = "Crear usuario", tags = "Post")
    public ResponseEntity<DTODetalleUsuarios> createUser(@RequestBody @Valid DTOCrearUsuario dto, UriComponentsBuilder uriComponentsBuilder)
    {
        DTODetalleUsuarios detalles = usuarioServicio.createNewUser(dto);

        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(detalles.code()).toUri();

        return ResponseEntity.created(url).body(detalles);
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Actualizar usuario", tags = "Put")
    public ResponseEntity<DTODetalleUsuarios> actualizarUsuario(@PathVariable Integer id,@RequestBody DTOActualizarUsuario dto)
    {
        DTODetalleUsuarios dtoUserMoreDetails = usuarioServicio.updateUser(Long.valueOf(id), dto);

        return ResponseEntity.ok(dtoUserMoreDetails);
    }
}
