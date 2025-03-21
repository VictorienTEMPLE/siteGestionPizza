package com.accenture.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//Spring va regarder dans les classes @Configuration, chercher les @Bean, et créer un instance dans le conteneur de spring à utiliser
public class ApiConfiguration {
    @Bean
    public OpenAPI apiConfiguration() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion de Pizzeria")
                        .description("Api pour l'application Gestion de Pizzeria")
                        .version("1.0.1")
                        .contact(new Contact()
                                .name("Victorien Temple et Tianhong Hamel-Huang")
                                .email("victorien-tianhong@accenture.com"))
                );
    }
}


