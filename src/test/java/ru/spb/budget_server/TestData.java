package ru.spb.budget_server;

import org.springframework.stereotype.Component;
import ru.spb.budget_server.model.Entry;
import ru.spb.budget_server.model.Operations;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TestData {

    public static String SUGAR_ITEM_OF_EXPENDITURE = "sugar";
    public static Integer CREATED_AMOUNT_ENTRY_FIRST = 100;
    public static Integer CREATED_BALANCE_ENTRY_FIRST = 100;
    public static Integer CREATED_AMOUNT_ENTRY_SECOND = 50;
    public static Integer CREATED_BALANCE_ENTRY_SECOND = 50;

    public static Integer UPDATED_AMOUNT_ENTRY_FIRST = 150;
    public static Integer UPDATED_BALANCE_ENTRY_FIRST = 150;
    public static Integer UPDATED_AMOUNT_ENTRY_SECOND = 50;
    public static Integer UPDATED_BALANCE_ENTRY_SECOND = 100;


    public Entry buildTestEntryFirst() {
        Entry entry1 = Entry.builder().
                id(1).
                date(LocalDate.now()).
                time(LocalTime.of(15, 20)).
                operations(Operations.REVENUE).
                amount(CREATED_AMOUNT_ENTRY_FIRST).
                balance(CREATED_BALANCE_ENTRY_FIRST).
                build();
        return entry1;
    }

    public Entry buildTestEntrySecond() {
        Entry entry2 = Entry.builder().
                id(2).
                date(LocalDate.now()).
                time(LocalTime.of(16, 10)).
                operations(Operations.EXPENDITURE).
                amount(CREATED_AMOUNT_ENTRY_SECOND).
                itemOfExpenditure(SUGAR_ITEM_OF_EXPENDITURE).
                balance(CREATED_BALANCE_ENTRY_SECOND).
                build();
        return entry2;
    }

    public Entry buildUpdatedTestEntryFirst() {
        Entry entry1 = Entry.builder().
                date(LocalDate.now()).
                time(LocalTime.of(15, 20)).
                operations(Operations.REVENUE).
                amount(UPDATED_AMOUNT_ENTRY_FIRST).
                balance(UPDATED_BALANCE_ENTRY_FIRST).
                build();
        return entry1;
    }

    public Entry buildUpdatedTestEntrySecond() {
        Entry entry2 = Entry.builder().
                date(LocalDate.now()).
                time(LocalTime.of(16, 10)).
                operations(Operations.EXPENDITURE).
                amount(UPDATED_AMOUNT_ENTRY_SECOND).
                itemOfExpenditure(SUGAR_ITEM_OF_EXPENDITURE).
                balance(UPDATED_BALANCE_ENTRY_SECOND).
                build();
        return entry2;
    }

}
