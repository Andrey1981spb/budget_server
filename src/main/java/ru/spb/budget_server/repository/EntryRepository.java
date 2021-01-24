package ru.spb.budget_server.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.spb.budget_server.model.Entry;

@Repository
public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {
}
