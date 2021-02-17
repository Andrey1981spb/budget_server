package ru.spb.budget_server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.model.Operations;
import ru.spb.budget_server.repository.EntryRepositoryImpl;

/**
 * Author A.Dmitriev
 */
@Service
@AllArgsConstructor
public class EntryService {

    private EntryRepositoryImpl entryRepository;

    public Flux<Entry> getAll() {
        return entryRepository.findAll();
    }

    public Mono<Entry> createEntry(Entry entry) {
        return entryRepository.save(entry);
    }

    public Flux<Entry> updateEntry(Entry incomeEntry, Integer previousBalance) {
        int id = incomeEntry.getId();
        Mono<Entry> previousMonoEntry = null;
        // async
        if (previousBalance == null) {
            previousMonoEntry = findPreviousEntry(id);
        }
        Mono<Entry> nextMonoEntry = findPreviousEntry(id);
        // not async
        Operations operation = incomeEntry.getOperations();
        int amount = incomeEntry.getAmount();
        Entry previousEntry = previousMonoEntry.block();
        int balance = calculateBalance(operation, previousEntry, amount);

        // think about builder
        incomeEntry.setBalance(balance);
        Entry nextEntry = nextMonoEntry.block();
        Flux<Entry> nextElementsFlow = updateEntry(nextEntry, balance);

        return Flux.concat(Mono.just(incomeEntry)).concatWith(nextElementsFlow);
    }

    private Mono<Entry> findPreviousEntry(Integer id) {
        Mono<Entry> previousMonoEntry = null;
        if (id != null) {
            previousMonoEntry = entryRepository.findById(String.valueOf(id - 1))
        }
        return previousMonoEntry;
    }

    private Mono<Entry> findNextEntry(Integer id) {
        //TODO
        return null;
    }

    private int calculateBalance(Operations operation, Entry previousEntry, int amount) {
        //TODO
        return 0;
    }

}
