package com.challengehub.forohub.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challengehub.forohub.DTOs.DTOCrearRespuesta;
import com.challengehub.forohub.DTOs.DTOGuardarRespuesta;
import com.challengehub.forohub.DTOs.DTOBorrarRespuesta;
import com.challengehub.forohub.DTOs.DTOObtenerInfoRespuesta;
import com.challengehub.forohub.DTOs.DTOObtenerRespuestasTopico;
import com.challengehub.forohub.DTOs.DTOActualizarRespuesta;
import com.challengehub.forohub.modelos.Respuesta;
import com.challengehub.forohub.modelos.Topico;
import com.challengehub.forohub.modelos.Usuario;
import com.challengehub.forohub.repositorios.RespuestaRepositorio;
import com.challengehub.forohub.repositorios.TopicoRepositorio;
import com.challengehub.forohub.repositorios.UsuarioRepositorio;

import jakarta.validation.ValidationException;

@Service
public class RespuestaServicio
{
    @Autowired
    RespuestaRepositorio responsesRepository;

    @Autowired
    TopicoRepositorio topicRepository;

    @Autowired
    UsuarioRepositorio userRepository;

    public DTOObtenerInfoRespuesta getResponseById(int responseId)
    {
        Optional<Respuesta> responseGetter = responsesRepository.findById(Long.valueOf(responseId));

        if(responseGetter.isEmpty())
        {
            throw new ValidationException("Inserte un codigo correcto...");
        }

        Respuesta response = responseGetter.get();

        DTOObtenerInfoRespuesta dtoResponseInfoResponse = new DTOObtenerInfoRespuesta(response.getCode(),
                response.getTopic().getCode(),
                response.getMessage(),
                response.getSolution(),
                response.getCreationdate(),
                response.getAuthor().getUsername());

        return dtoResponseInfoResponse;
    }

    public DTOObtenerRespuestasTopico createResponse(DTOCrearRespuesta dtoCreateResponse)
    {
        //Find the objects with the IDs
        Optional<Usuario> userGetter = userRepository.findById(Long.valueOf(dtoCreateResponse.idAuthor()));
        Optional<Topico> topicGetter = topicRepository.findById(Long.valueOf(dtoCreateResponse.idTopic()));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("Ese usuario no existe");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("Ese topico no existe");
        }

        DTOGuardarRespuesta dtoCreateResponseToDatabase = new DTOGuardarRespuesta(
                dtoCreateResponse.message(),
                topicGetter.get(),
                userGetter.get(),
                dtoCreateResponse.solution()
        );

        Respuesta response = new Respuesta(dtoCreateResponseToDatabase);

        responsesRepository.save(response);

        return fillData(topicGetter.get(), response);
    }

    public DTOObtenerRespuestasTopico updateResponse(Long idResponse, DTOActualizarRespuesta dtoUpdateResponse)
    {
        //Find the objects with the IDs
        Optional<Usuario> userGetter = userRepository.findById(Long.valueOf(dtoUpdateResponse.idAuthor()));
        Optional<Topico> topicGetter = topicRepository.findById(Long.valueOf(dtoUpdateResponse.idTopic()));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("Ese usuario no existe");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("Ese topico no existe");
        }

        Respuesta response = responsesRepository.findById(idResponse).get();

        response.setMessage(dtoUpdateResponse.message());
        response.setSolution(dtoUpdateResponse.solution());
        response.setAuthor(userGetter.get());
        response.setTopic(topicGetter.get());

        return fillData(topicGetter.get(), response);
    }

    public DTOBorrarRespuesta deleteResponse(Long id)
    {
        try
        {
            DTOBorrarRespuesta dtoResponseDeleteResponse = new DTOBorrarRespuesta(200,
                    "La respuesta se eliminó correctamente");

            responsesRepository.deleteById(id);

            return dtoResponseDeleteResponse;
        }
        catch (Exception e)
        {
            throw new ValidationException("Un error ocurrió al eliminar la respuesta");
        }
    }

    public List<DTOObtenerRespuestasTopico> getResponsesByTopic(int topicId)
    {
        Optional<Topico> topicGetter = topicRepository.findById(Long.valueOf(topicId));
        List<DTOObtenerRespuestasTopico> listResponses = new ArrayList<>();

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("Ese usuario no existe");
        }

        Topico topic = topicGetter.get();

        List<Respuesta> responsesList =  responsesRepository.findByTopicId(topicId);

        for(Respuesta r: responsesList)
        {
            DTOObtenerRespuestasTopico dtoResponsesInfoOfResponsesTopic = fillData(topic, r);

            listResponses.add(dtoResponsesInfoOfResponsesTopic);
        }

        return listResponses;
    }

    public DTOObtenerRespuestasTopico fillData(Topico topic, Respuesta response)
    {
        DTOObtenerRespuestasTopico dtoResponsesInfoOfResponsesTopic = new DTOObtenerRespuestasTopico(
                topic.getCode(),
                topic.getMessage(),
                response.getCode(),
                response.getMessage(),
                response.getSolution(),
                response.getCreationdate(),
                response.getAuthor().getUsername()
                );

        return dtoResponsesInfoOfResponsesTopic;
    }
}