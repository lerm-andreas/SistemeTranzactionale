package com.sisteme.tranzationale.data;

import java.util.ArrayList;
import java.util.List;

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
}
