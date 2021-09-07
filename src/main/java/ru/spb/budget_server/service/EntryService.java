package ru.spb.budget_server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.model.Operations;
import ru.spb.budget_server.repository.impl.EntryRepositoryImpl;

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
        Mono<Entry> updatedMonoEntry = entryRepository.save(incomeEntry);
        Entry updatedEntry = updatedMonoEntry.block();
        Integer updatedEntryId = updatedEntry.getId();
        Mono<Entry> previousMonoEntry = null;
        // async
        if (previousBalance == null) {
            previousMonoEntry = findPreviousEntry(updatedEntryId);
        }
        Mono<Entry> nextMonoEntry = findNextEntry(updatedEntryId);
        int balance = calculateBalance(updatedEntry, previousMonoEntry, previousBalance);
        updatedEntry.setBalance(balance);

        Flux<Entry> nextElementsFlow = getNextElementsFlow(nextMonoEntry, balance);
        return Flux.concat(Mono.just(updatedEntry)).concatWith(nextElementsFlow);
    }

    private Mono<Entry> findPreviousEntry(Integer id) {
        return entryRepository.findById(String.valueOf(id - 1));
    }

    private Mono<Entry> findNextEntry(Integer id) {
        return entryRepository.findById(String.valueOf(id - 1));
    }

    private int calculateBalance(Entry updatedEntry, Mono<Entry> previousMonoEntry, Integer previousEntryBalance) {
        Operations operation = updatedEntry.getOperations();
        int amount = updatedEntry.getAmount();
        int newBalance = 0;
        Entry previousEntry = previousMonoEntry.block();
        if (previousEntryBalance == null) {
            previousEntryBalance = previousEntry.getBalance();
        }
        switch (operation) {
            case REVENUE:
                return previousEntryBalance + amount;
            case EXPENDITURE:
                return previousEntryBalance - amount;
        }
        return newBalance;
    }

    private Flux<Entry> getNextElementsFlow(Mono<Entry> nextMonoEntry, int balance) {
        Entry nextEntry = nextMonoEntry.block();
        Flux<Entry> nextElementsFlow = null;
        if (nextEntry != null) {
            nextElementsFlow = updateEntry(nextEntry, balance);
        }
        return nextElementsFlow;
    }

}
