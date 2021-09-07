package ru.spb.budget_server.router;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * Роутер входящих веб-запросов
 * Author A.Dmitriev
 */
@Configuration
@AllArgsConstructor
public class RouterConfig {

    private EntryHandler entryHandler;

    @Bean
    public RouterFunction<ServerResponse> route(EntryHandler entryHandler) {
        return RouterFunctions.route(GET("/entry/{id}").
                and(accept(MediaType.APPLICATION_STREAM_JSON)),
                entryHandler::findById);
    }

}
