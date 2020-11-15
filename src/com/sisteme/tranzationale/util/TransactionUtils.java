package com.sisteme.tranzationale.util;

import com.sisteme.tranzationale.data.Transaction;

import java.util.List;

public class TransactionUtils {

    public static void print(List<Transaction> transactions) {
        transactions.forEach(System.out::println);
    }

    public static void printWithIndex(List<String> transactions) {
        int index = 1;

        for (String transaction : transactions) {
            System.out.println(index + ": " + transaction);
            index++;
        }
    }

}
