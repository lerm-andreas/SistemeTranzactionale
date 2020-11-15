package com.sisteme.tranzationale.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Transaction {

    private List<Operation> operations;

    public Transaction() {
        this.operations = new ArrayList<>();
    }

    public Transaction(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Transaction{" + operations + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(operations, that.operations);
    }
}
