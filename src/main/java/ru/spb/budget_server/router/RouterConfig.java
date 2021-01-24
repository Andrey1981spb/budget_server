package ru.spb.budget_server.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.spb.budget_server.service.EntryService;

@Configuration
public class RouterConfig {

    @Autowired
    private EntryService entryService;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return null;
    }

}
