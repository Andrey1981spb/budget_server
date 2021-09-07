package ru.spb.budget_server.router;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.repository.EntryRepository;
import ru.spb.budget_server.service.EntryService;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Обработчик перенаправленных роутером входящих веб-запросов
 * Author A.Dmitriev
 */
@Component
@AllArgsConstructor
public class EntryHandler {

    private EntryService entryService;
    private EntryRepository entryRepository;


    public Mono<ServerResponse> findById(final ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(entryRepository.findById(id), Entry.class);
    }



}
