package ru.spb.budget_server.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * Конфигурация для соединения с базой данных
 *
 * Author A.Dmitriev
 */
@Configuration
@EnableReactiveMongoRepositories("ru.spb.budget_server.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${dbhost}")
    private String host;

    @Value("${dbport}")
    private String port;

    @Value("${dbname}")
    private String dbName;

    @Value("${entry_collection}")
    private String entryCollection;

    /**
     * Создает бин клиента Монго
     * @return бин клиента Монго с настройками хоста и порта
     */
    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(host + ":" + port);
    }

    /**
     * Создает бин базы данных Монго
     * @return бин базы данных Монго
     */
    public MongoDatabase mongoDatabase() {
        return reactiveMongoClient().getDatabase(dbName);
    }

    /**
     * Создает бин коллекции документов Монго
     * @return бин коллекции документов Монго
     */
    public MongoCollection<Document> mongoCollection() {
        return mongoDatabase().getCollection(entryCollection);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    private ReactiveMongoDatabaseFactory mongoDbFactory() {
        return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(),
                getDatabaseName());
    }

    /**
     * Создает бин шаблона реактивного Монго
     * @return бин шаблона реактивного Монго
     */
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }
}
