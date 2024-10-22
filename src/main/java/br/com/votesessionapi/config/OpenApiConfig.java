package br.com.votesessionapi.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vote Session API")
                        .version("1.0.0")
                        .description("Esta API de Votação permite que os usuários criem e gerenciem sessões de votação para tópicos específicos. Com funcionalidades como abertura de sessões, registro de votos e consulta de resultados, a API é ideal para desenvolvedores que desejam integrar recursos de votação em suas aplicações. A API é projetada para ser fácil de usar e se integra perfeitamente a sistemas de gerenciamento de eventos e plataformas de feedback."));
    }

}