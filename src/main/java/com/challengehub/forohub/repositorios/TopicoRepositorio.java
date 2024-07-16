package com.challengehub.forohub.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challengehub.forohub.modelos.Topico;



public interface TopicoRepositorio extends JpaRepository<Topico, Long>
{
    @Query("Select t FROM Topico t WHERE t.title=:paramTitle or t.message=:paramMessage")
    public Topico findTopicByTitleOrMessage(String paramTitle, String paramMessage);

    @Query("Select t FROM Topico t ORDER BY creationDate DESC LIMIT 10")
    public List<Topico> findLastTenRecordsByCreationDate();

    @Query(nativeQuery = true, value = """
            SELECT t.code,
            t.title,
            t.message,
            t.creation_date,
            t.status,
            t.author,
            t.course
            FROM topic as t
            INNER JOIN course c
            ON t.course = c.code
            WHERE UPPER(c.name) = :courseName
            AND YEAR(creation_date) = :year
            """)
    public List<Topico> findTopicByCourseNameAndYear(String courseName, Integer year);
}
