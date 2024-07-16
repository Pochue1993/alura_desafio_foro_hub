package com.challengehub.forohub.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class ConfiguracionSpringDoc {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Desafio oracle ONE")
                        .description("Es una API REST que contiene los metodos para hacer "
                        		+ "'CRUD' para respuestas, usuarios y topicos")
                        .contact(new Contact()
                                .name("Josué Abrahan Ramos")
                                .email("josueramos1293@gmail.com"))
                )
                .components(
                        new Components()
                        .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}

