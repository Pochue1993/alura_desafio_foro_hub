package com.challengehub.forohub.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challengehub.forohub.DTOs.DTOGuardarTopico;
import com.challengehub.forohub.modelos.Topico;
import com.challengehub.forohub.repositorios.TopicoRepositorio;

import jakarta.validation.ValidationException;

@Component
public class ValidarTituloOMensaje implements IValidacionTopico
{
    @Autowired
    TopicoRepositorio topicRepository;

    @Override
    public void checkValidation(DTOGuardarTopico dataTopic)
    {
        Topico topicData = topicRepository.findTopicByTitleOrMessage(dataTopic.title(), dataTopic.message());

        if(topicData != null)
        {
            throw new ValidationException("Ya existe un topico con ese titulo");
        }
    }
}
