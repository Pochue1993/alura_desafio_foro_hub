package com.challengehub.forohub.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challengehub.forohub.modelos.Respuesta;
import com.challengehub.forohub.modelos.Topico;



public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long>
{
    @Query("SELECT r from Respuesta r WHERE r.topic=:codeTopic")
    List<Respuesta> findByTopic(Topico codeTopic);

    @Query(nativeQuery = true, value = """
            SELECT r.code,
            r.message,
            r.topic,
            r.creationdate,
            r.author,
            r.solution
            FROM responses as r
            WHERE r.topic = :codeTopic
            """)
    List<Respuesta> findByTopicId(Integer codeTopic);
}
