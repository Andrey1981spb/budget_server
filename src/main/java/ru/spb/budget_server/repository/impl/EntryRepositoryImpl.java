package ru.spb.budget_server.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.repository.EntryRepository;

/**
 * Реактивный репозиторий записей бюджетного учета сохранением в MongoDb.
 *
 * Author A.Dmitriev
 */
@AllArgsConstructor
@Repository
public class EntryRepositoryImpl {

    private EntryRepository entryRepository;

    @Autowired
    public void setEntryRepository(final EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Mono<Entry> save(final Entry entity) {
        return entryRepository.save(entity);
    }

    public Flux<Entry> findAll() {
        return entryRepository.findAll();
    }

    public Mono<Entry> findById(final String id) {
        return entryRepository.findById(id);
    }

}
