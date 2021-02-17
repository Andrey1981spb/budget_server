package ru.spb.budget_server.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.budget_server.repository.EntryRepositoryImpl;

@Configuration
public class TestConfiguration {

    @Bean
    public EntryRepositoryImpl mockEntryRepositoryImpl() {
        return Mockito.mock(EntryRepositoryImpl.class);
    }

}
