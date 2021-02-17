package ru.spb.budget_server.repository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;

/**
 * Created A.Dmitriev
 */
@AllArgsConstructor
public class EntryRepositoryImpl {

    EntryRepository entryRepository;

    public Mono<Entry> save(Entry entity) {
        return null;
    }

    public Flux<Entry> findAll() {
        return null;
    }

    public Mono<Entry> findById (String id) {
        return null;
    }

}
