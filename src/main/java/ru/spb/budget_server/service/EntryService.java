package ru.spb.budget_server.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;

@Service
public class EntryService {

    public Flux<Entry> getAll () {
        return Flux.empty();
    }

    public Mono<Entry> createEntry (Entry entry) {
        return null;
    }

}
