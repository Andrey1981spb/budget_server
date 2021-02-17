package ru.spb.budget_server;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.model.Operations;
import ru.spb.budget_server.router.EntryHandler;
import ru.spb.budget_server.router.RouterConfig;
import ru.spb.budget_server.service.EntryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RouterConfig.class, EntryHandler.class})
@SpringBootTest(classes = TestData.class)
public class RouteIntegrationTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private TestData testData;
    @MockBean
    private EntryService entryService;
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
    }

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void updateEntry() {
        Entry updatedFirstEntry = testData.buildUpdatedTestEntryFirst();
        Entry updatedSecondEntry = testData.buildUpdatedTestEntrySecond();

        when(entryService.updateEntry(updatedFirstEntry, 1)).
                thenReturn(Flux.just(updatedFirstEntry, updatedSecondEntry));

        webTestClient.put().
                uri("/entries/{id}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                body(Mono.just(updatedFirstEntry), Entry.class).
                exchange().
                expectStatus().isOk().
                expectBody(Entry.class).
                value(response -> {
                    Assertions.assertEquals(response.getBalance(), 100);
                });
    }

    @Test
    public void getAllEntry() {
        Entry entryFirst = testData.buildTestEntryFirst();
        Entry entrySecond = testData.buildTestEntrySecond();

        when(entryService.getAll()).
                thenReturn(Flux.just(entryFirst, entrySecond));

        webTestClient.get().
                uri("/entries").
                exchange().
                expectStatus().isOk().
                expectBodyList(Entry.class).
                value(response -> {
                    Assertions.assertEquals(response.get(0), entryFirst);
                    Assertions.assertEquals(response.get(1), entrySecond);
                });
    }

    @Test
    public void createEntry() {
        Entry entryNew = testData.buildTestEntryFirst();
        when(entryService.createEntry(any())).thenReturn(Mono.just(entryNew));

        webTestClient.post().
                uri("/entries").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                body(Mono.just(entryNew), Entry.class).
                exchange().
                expectStatus().isOk().
                expectBody(Entry.class).
                value(response -> {
                    Assertions.assertEquals(response.getId(), 0);
                    Assertions.assertEquals(response.getOperations(), Operations.REVENUE);
                    Assertions.assertEquals(response.getAmount(), 100);
                });
    }

}
