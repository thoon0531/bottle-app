package com.bottle_app.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(@Value("${app.description})") String description,
                                 @Value("${app.version}") String version) {
        Server server = new Server().url("https://bottle-server.site");
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("Bottle App Server API")
                        .version(version)
                        .description(description)
                        .termsOfService("http://swagger.io/terms/"));
    }

}
