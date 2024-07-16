package com.challengehub.forohub.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challengehub.forohub.servicios.RespuestaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/respuetastopico")
@SecurityRequirement(name = "bearer-key")
public class RespuestaTopicoControlador
{
    @Autowired
    RespuestaServicio respuestaServicio;

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene las respuestas de un topico", tags = "Get")
    public ResponseEntity obtenerRespuestas(@PathVariable Integer id)
    {
        return ResponseEntity.ok(respuestaServicio.getResponsesByTopic(id));
    }
}
