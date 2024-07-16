package com.challengehub.forohub.controladores;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challengehub.forohub.DTOs.DTOCrearRespuesta;
import com.challengehub.forohub.DTOs.DTOObtenerRespuestasTopico;
import com.challengehub.forohub.DTOs.DTOActualizarRespuesta;
import com.challengehub.forohub.servicios.RespuestaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaControlador
{
    @Autowired
    RespuestaServicio respuestaServicio;

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una respuesta en especifica por id", tags = "Get")
    public ResponseEntity obtenerRespuestasPorTopico(@PathVariable Integer id)
    {
        return ResponseEntity.ok(respuestaServicio.getResponseById(id));
    }

    @PostMapping
    @Operation(summary = "Obtiene las respuestas de un determinado topico", tags = "Post")
    public ResponseEntity ObtenerRespuestaPorTopico(@RequestBody DTOCrearRespuesta dtoCreateResponse, UriComponentsBuilder uriComponentsBuilder )
    {
        DTOObtenerRespuestasTopico dtoResponsesInfoOfResponsesTopic = respuestaServicio.createResponse(dtoCreateResponse);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(dtoResponsesInfoOfResponsesTopic.codeResponse()).toUri();

        return ResponseEntity.created(url).body(dtoResponsesInfoOfResponsesTopic);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza la respuesta", tags = "Put")
    @Transactional
    public ResponseEntity actualizarRespuesta(@PathVariable Integer id, @RequestBody DTOActualizarRespuesta dtoUpdateResponse)
    {
        return ResponseEntity.ok(respuestaServicio.updateResponse(Long.valueOf(id), dtoUpdateResponse));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra una respuesta", tags = "Delete")
    @Transactional
    public ResponseEntity borrarRespuesta(@PathVariable Integer id)
    {
        return ResponseEntity.ok(respuestaServicio.deleteResponse(Long.valueOf(id)));
    }

}
