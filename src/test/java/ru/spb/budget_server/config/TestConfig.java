package ru.spb.budget_server.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import ru.spb.budget_server.repository.EntryRepository;
import ru.spb.budget_server.repository.impl.EntryRepositoryImpl;

@TestConfiguration
@EnableReactiveMongoRepositories()
public class TestConfig {

    @Bean
    public EntryRepositoryImpl mockEntryRepositoryImpl() {
        return Mockito.mock(EntryRepositoryImpl.class);
    }

}
