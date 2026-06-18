package com.techchallenge.gastrohub.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gastro-Hub Backend")
                        .version("1.0.0")
                        .description("""
                        Sistema de gestão para o grupo de restaurantes da região.
                        
                        **Integrantes do Grupo:**
                        * Felipe Ulson Sora - RM370766
                        * Gabriel Alberto Ferreira Pinto - RM374005
                        * Jeniffer da Nobrega Bandeira - RM3711936
                        * Marco Antônio de Oliveira Gomes - RM372323
                        * Ricardo Aguirra Menendes - RM373817
                        """)
                        .contact(new Contact()
                                .name("Grupo 17 - Postech ADJ")
                                .url("https://github.com/Gaalfp/gastro-hub-backend"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}