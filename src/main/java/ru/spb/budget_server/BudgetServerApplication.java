package ru.spb.budget_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import ru.spb.budget_server.repository.EntryRepository;

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories(basePackageClasses = {EntryRepository.class})
public class BudgetServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetServerApplication.class, args);
    }

}
