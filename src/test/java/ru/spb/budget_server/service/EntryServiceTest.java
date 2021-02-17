package ru.spb.budget_server.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.spb.budget_server.TestData;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.repository.EntryRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.spb.budget_server.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestData.class)
public class EntryServiceTest {

    @Autowired
    private EntryRepositoryImpl entryRepository;
    @Autowired
    private EntryService entryService;

    private TestData testData;

    @Test
    void contextLoads() {
    }

    @Test
    public void testUpdateEntry() {
        Entry updatedFirstEntry = testData.buildUpdatedTestEntryFirst();
        Entry updatedSecondEntry = testData.buildUpdatedTestEntrySecond();
        Mockito.when(entryRepository.save(updatedFirstEntry)).thenReturn(Mono.just(updatedFirstEntry));
        Mockito.when(entryRepository.save(updatedSecondEntry)).thenReturn(Mono.just(updatedSecondEntry));

        Flux<Entry> entryFlux = entryService.updateEntry(updatedFirstEntry, 1);

        StepVerifier.
                create(entryFlux).
                assertNext(entry ->
                {
                    assertEquals(1, entry.getId());
                    assertEquals(UPDATED_BALANCE_ENTRY_FIRST, entry.getBalance());
                }).
                assertNext(entry -> {
                    assertEquals(2, entry.getId());
                    assertEquals(UPDATED_BALANCE_ENTRY_SECOND, entry.getBalance());
                }).
                expectComplete().
                verify();
    }

    @Test
    public void testGetAll() {
        Entry entryFirst = testData.buildTestEntryFirst();
        Entry entrySecond = testData.buildTestEntrySecond();
        Mockito.when(entryRepository.findAll()).thenReturn(Flux.just(entryFirst, entrySecond));

        Flux<Entry> entryFlux = entryService.getAll();

        StepVerifier.
                create(entryFlux).
                assertNext(entry -> assertEquals(CREATED_BALANCE_ENTRY_FIRST, entry.getBalance())).
                assertNext(entry -> assertEquals(CREATED_BALANCE_ENTRY_SECOND, entry.getBalance())).
                expectComplete().
                verify();
    }

    @Test
    public void testCreateEntry() {
        Entry entryFirst = testData.buildTestEntryFirst();
        Mockito.when(entryRepository.save(entryFirst)).thenReturn(Mono.just(entryFirst));

        Mono<Entry> entryMono = entryService.createEntry(entryFirst);

        StepVerifier.
                create(entryMono).
                assertNext(entry ->
                {
                    assertEquals(CREATED_BALANCE_ENTRY_FIRST, entry.getBalance());
                    assertEquals(1, entry.getId());
                }).
                expectComplete().
                verify();
    }

}
