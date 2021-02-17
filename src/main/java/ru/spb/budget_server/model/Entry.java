package ru.spb.budget_server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;

@Document
@TypeAlias("entry")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Entry {
    @Id
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private Operations operations;
    private Integer amount;

    @Field("item_of_expenditure")
    private String itemOfExpenditure;
    private Integer balance;
}
