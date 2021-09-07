package ru.spb.budget_server.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;

/**
 * Репозиторий записей бюджетного учета
 * Author A.Dmitriev
 */
public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {

    /**
     * Сохраняет запиись в базе данных
     *
     * @param entity запиись учета
     * @return сохраненная в базу данных запись учета, обернутая в {@link Mono}
     */
    <S extends Entry> Mono<S> save(S entity);

    /**
     * Извлекает все запииси ииз базы данных
     *
     * @return множество записей учета, обернутых в {@link Flux}
     */
    Flux<Entry> findAll();

    /**
     * Извлекает запиись из базы данных
     *
     * @param id уникательный идентиификатор запиисии учета
     * @return сохраненная в базу данных запись учета, обернутая в {@link Mono}
     */
    @Override
    Mono<Entry> findById(String id);
}
