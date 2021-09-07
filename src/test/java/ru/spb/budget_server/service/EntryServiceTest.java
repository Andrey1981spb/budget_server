package ru.spb.budget_server.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.spb.budget_server.TestData;
import ru.spb.budget_server.config.MongoConfig;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.repository.EntryRepository;
import ru.spb.budget_server.repository.impl.EntryRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;
import static ru.spb.budget_server.TestData.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest (includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {EntryService.class, TestData.class}))
public class EntryServiceTest {

    @Autowired
    private EntryRepositoryImpl entryRepositoryImpl;
    @Autowired
    private EntryService entryService;
    @Autowired
    private TestData testData;

    @Before
    public void setUp() {
        Entry previousEntry = testData.buildPreviousTestEntry();
        when(entryRepositoryImpl.save(previousEntry)).thenReturn(Mono.just(previousEntry));
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(entryRepositoryImpl);
    }

    @Test
    public void testUpdateEntry() {
        Entry entryForUpdate = testData.buildTestEntryFirst();
        Mockito.when(entryRepositoryImpl.save(entryForUpdate)).thenReturn(Mono.just(entryForUpdate));

        Entry updatedFirstEntry = testData.buildUpdatedTestEntryFirst();
        Entry updatedSecondEntry = testData.buildUpdatedTestEntrySecond();
        Mockito.when(entryRepositoryImpl.save(updatedFirstEntry)).thenReturn(Mono.just(updatedFirstEntry));
        Mockito.when(entryRepositoryImpl.save(updatedSecondEntry)).thenReturn(Mono.just(updatedSecondEntry));

        Flux<Entry> entryFlux = entryService.updateEntry(updatedFirstEntry, null);

        StepVerifier.
                create(entryFlux).
                assertNext(entry ->
                {
                    assertEquals(2, entry.getId());
                    assertEquals(UPDATED_BALANCE_ENTRY_FIRST, entry.getBalance());
                }).
                assertNext(entry -> {
                    assertEquals(3, entry.getId());
                    assertEquals(UPDATED_BALANCE_ENTRY_SECOND, entry.getBalance());
                }).
                expectComplete().
                verify();
    }

    @Test
    public void testCreateEntry() {
        Entry entryFirst = testData.buildTestEntryFirst();
        Mockito.when(entryRepositoryImpl.save(entryFirst)).thenReturn(Mono.just(entryFirst));

        Mono<Entry> entryMono = entryService.createEntry(entryFirst);

        StepVerifier.
                create(entryMono).
                assertNext(entry ->
                {
                    assertEquals(CREATED_BALANCE_ENTRY_FIRST, entry.getBalance());
                    assertEquals(2, entry.getId());
                }).
                expectComplete().
                verify();
    }

    @Test
    public void testGetAll() {
        Entry entryFirst = testData.buildTestEntryFirst();
        Entry entrySecond = testData.buildTestEntrySecond();
        Mockito.when(entryRepositoryImpl.findAll()).thenReturn(Flux.just(entryFirst, entrySecond));

        Flux<Entry> entryFlux = entryService.getAll();

        StepVerifier.
                create(entryFlux).
                assertNext(entry -> assertEquals(CREATED_BALANCE_ENTRY_FIRST, entry.getBalance())).
                assertNext(entry -> assertEquals(CREATED_BALANCE_ENTRY_SECOND, entry.getBalance())).
                expectComplete().
                verify();
    }

}
