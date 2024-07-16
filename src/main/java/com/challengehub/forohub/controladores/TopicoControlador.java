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

import com.challengehub.forohub.DTOs.DTOCrearTopico;
import com.challengehub.forohub.DTOs.DTOObtenerRegistrosTopico;
import com.challengehub.forohub.DTOs.DTOActualizarTopico;
import com.challengehub.forohub.DTOs.DTOBorrarTopico;
import com.challengehub.forohub.servicios.TopicoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoControlador
{
    @Autowired
    TopicoServicio topicoServicio;

    @GetMapping
    @Operation(summary = "Obtener topicos", tags = "Get")
    public ResponseEntity obtenerTopicos()
    {
        return ResponseEntity.ok(topicoServicio.getAllDataTopic());
    }

    @PostMapping
    @Operation(summary = "Crear topico", tags = "Post")
    public ResponseEntity<DTOObtenerRegistrosTopico> crearTopico(@RequestBody @Valid DTOCrearTopico dto, UriComponentsBuilder uriComponentsBuilder)
    {
        DTOObtenerRegistrosTopico topicCreated = topicoServicio.createTopic(dto);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicCreated.id()).toUri();

        return ResponseEntity.created(url).body(topicCreated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener topico", tags = "Get")
    public ResponseEntity<DTOObtenerRegistrosTopico> obtenerTopico(@PathVariable Long id)
    {
        return ResponseEntity.ok(topicoServicio.getTopicById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar topicos", tags = "Put")
    public ResponseEntity<DTOObtenerRegistrosTopico> actualizarTopico(@PathVariable Long id, @RequestBody DTOActualizarTopico dto)
    {
        DTOObtenerRegistrosTopico dtoResponseGetDataTopic = topicoServicio.updateTopic(id, dto);

        return ResponseEntity.ok(dtoResponseGetDataTopic);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Borrar topico", tags = "Delete")
    public ResponseEntity<DTOBorrarTopico> borrarTopico(@PathVariable Long id)
    {
       return ResponseEntity.ok(topicoServicio.DeleteTopic(id));
    }
}
