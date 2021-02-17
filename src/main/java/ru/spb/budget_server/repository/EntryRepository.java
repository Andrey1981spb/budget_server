package ru.spb.budget_server.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;

@Repository
public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {

    <S extends Entry> Mono<S> save(S entity);
    Flux<Entry> findAll();

    @Override
    Mono<Entry> findById(String id);
}
