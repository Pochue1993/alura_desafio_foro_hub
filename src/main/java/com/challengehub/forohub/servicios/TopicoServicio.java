package com.challengehub.forohub.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challengehub.forohub.DTOs.DTOCrearTopico;
import com.challengehub.forohub.DTOs.DTOGuardarTopico;
import com.challengehub.forohub.DTOs.DTOBorrarTopico;
import com.challengehub.forohub.DTOs.DTOObtenerRegistrosTopico;
import com.challengehub.forohub.DTOs.DTOObtenerTopico;
import com.challengehub.forohub.DTOs.DTOBuscarTopicoFechaTitulo;
import com.challengehub.forohub.DTOs.DTOActualizarTopico;
import com.challengehub.forohub.DTOs.DTOUsuario;
import com.challengehub.forohub.modelos.Curso;
import com.challengehub.forohub.modelos.Respuesta;
import com.challengehub.forohub.modelos.Estado;
import com.challengehub.forohub.modelos.Topico;
import com.challengehub.forohub.modelos.Usuario;
import com.challengehub.forohub.repositorios.CursoRepositorio;
import com.challengehub.forohub.repositorios.RespuestaRepositorio;
import com.challengehub.forohub.repositorios.EstadoRepositorio;
import com.challengehub.forohub.repositorios.TopicoRepositorio;
import com.challengehub.forohub.repositorios.UsuarioRepositorio;

import jakarta.validation.ValidationException;

@Service
public class TopicoServicio
{
    @Autowired
    TopicoRepositorio topicRepository;

    @Autowired
    RespuestaRepositorio responsesRepository;

    @Autowired
    UsuarioRepositorio userRepository;

    @Autowired
    CursoRepositorio courseRepository;

    @Autowired
    EstadoRepositorio statusRepository;

    @Autowired
    List<IValidacionTopico> validationTopics;

    public List<DTOObtenerRegistrosTopico> getAllDataTopic()
    {
        List<Topico> listTopics = topicRepository.findAll();
        List<DTOObtenerRegistrosTopico> topics = new ArrayList<>();

        for(Topico t :listTopics)
        {
            DTOObtenerRegistrosTopico dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public DTOObtenerRegistrosTopico createTopic(DTOCrearTopico dtoCreateTopic)
    {
        //Find the objects with the IDs
        Optional<Usuario> userGetter = userRepository.findById(Long.valueOf(dtoCreateTopic.user()));
        Optional<Curso> courseGetter = courseRepository.findById(Long.valueOf(dtoCreateTopic.course()));
        Optional<Estado> statusGetter =statusRepository.findById(Long.valueOf(1));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("Ese usuario no existe");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("Ese curso no existe");
        }

        if(statusGetter.isEmpty())
        {
            throw new ValidationException("El codigo del estado es incorrecto");
        }

        DTOGuardarTopico dtoCreateTopicToDatabase = new DTOGuardarTopico(
                dtoCreateTopic.title(),
                dtoCreateTopic.message(),
                userGetter.get(),
                courseGetter.get(),
                statusGetter.get()
        );

        validationTopics.forEach(t -> t.checkValidation(dtoCreateTopicToDatabase));

        Topico topicData = new Topico(dtoCreateTopicToDatabase);

        topicRepository.save(topicData);

        DTOObtenerRegistrosTopico dataTopic =fillDtoGetDataTopic(topicData);

        return dataTopic;
    }

    public DTOObtenerRegistrosTopico getTopicById(Long id)
    {
        Optional<Topico> optional_Topic = topicRepository.findById(id);

        if(optional_Topic.isEmpty())
        {
            throw new ValidationException("Ese topico no existe");
        }

        Topico topicData = optional_Topic.get();

        DTOObtenerRegistrosTopico dataTopic =fillDtoGetDataTopic(topicData);

        return dataTopic;
    }

    public DTOObtenerRegistrosTopico updateTopic(Long id, DTOActualizarTopico dtoUpdateTopic)
    {
        Optional<Topico> topicGetter = topicRepository.findById(id);

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("Ese topico no existe");
        }

        Topico topic = topicGetter.get();

        Optional<Usuario> userGetter = userRepository.findById(Long.valueOf(dtoUpdateTopic.user()));
        Optional<Curso> courseGetter = courseRepository.findById(Long.valueOf(dtoUpdateTopic.course()));
        Optional<Estado> statusGetter =statusRepository.findById(Long.valueOf(1));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("Ese usuario no existe");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("Ese curso no existe");
        }

        if(statusGetter.isEmpty())
        {
            throw new ValidationException("El codigo de estado no existe");
        }

        topic.setMessage(dtoUpdateTopic.message());
        topic.setTitle(dtoUpdateTopic.title());
        topic.setAuthor(userGetter.get());
        topic.setCourse(courseGetter.get());
        topic.setStatus(statusGetter.get());

        DTOObtenerRegistrosTopico dtoResponseGetDataTopic = fillDtoGetDataTopic(topic);

        return dtoResponseGetDataTopic;
    }

    public DTOBorrarTopico DeleteTopic(Long id)
    {
        DTOBorrarTopico dtoResponseDeleteTopic = new DTOBorrarTopico(200,
                "El topico fue eliminado correctamente");

        topicRepository.deleteById(id);

        return dtoResponseDeleteTopic;
    }

    public List<DTOObtenerRegistrosTopico> findLastTenRecords()
    {
        List<Topico> listTopics = topicRepository.findLastTenRecordsByCreationDate();
        List<DTOObtenerRegistrosTopico> topics = new ArrayList<>();

        for(Topico t :listTopics)
        {
            DTOObtenerRegistrosTopico dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public List<DTOObtenerRegistrosTopico> findTopicByTitleAndYear(DTOBuscarTopicoFechaTitulo dtoTopicSearchTitleAndYear)
    {
        List<Topico> listTopics = topicRepository.findTopicByCourseNameAndYear(dtoTopicSearchTitleAndYear.courseName(),
                dtoTopicSearchTitleAndYear.year());
        List<DTOObtenerRegistrosTopico> topics = new ArrayList<>();

        for(Topico t :listTopics)
        {
            DTOObtenerRegistrosTopico dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public DTOObtenerRegistrosTopico fillDtoGetDataTopic(Topico topicData)
    {
        List<Respuesta> listResponses = responsesRepository.findByTopic(topicData);

        //Obtain the list of Responses
        List<DTOObtenerTopico> listDtoResponseTopic = listResponses.stream()
                .map(r -> new DTOObtenerTopico(r.getCreationdate(),
                        r.getMessage(),
                        r.getSolution(),
                        new DTOUsuario(r.getAuthor().getUsername(), r.getAuthor().getEmail())))
                .toList();

        DTOObtenerRegistrosTopico dtoResponseGetDataTopic = new DTOObtenerRegistrosTopico(
                topicData.getCode(),
                topicData.getTitle(),
                topicData.getMessage(),
                topicData.getCreationDate(),
                topicData.getStatus().getDescription(),
                new DTOUsuario(topicData.getAuthor().getUsername(), topicData.getAuthor().getEmail()),
                topicData.getCourse().getName(),
                listDtoResponseTopic
        );

        return dtoResponseGetDataTopic;
    }
}
