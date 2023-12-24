package com.kagan.flightapidemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Flight Search API",
        description = "OpenApi description for spring boot API to search for flights.",
        contact = @Contact(
                name = "Melik Kağan",
                email = "kaganyaylagulu@gmail.com"
        )
))
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("com.kagan.flightapidemo")
                .build();
    }
}

