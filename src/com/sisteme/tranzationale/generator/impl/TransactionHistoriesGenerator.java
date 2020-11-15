package com.sisteme.tranzationale.generator.impl;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.generator.PermutationService;
import com.sisteme.tranzationale.util.TransactionUtils;
import com.sisteme.tranzationale.validator.TransactionValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionHistoriesGenerator implements PermutationService {

    private TransactionValidator transactionValidator;

    List<Transaction> transactions;
    List<String> allHistories;

    public TransactionHistoriesGenerator(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
        transactions = new ArrayList<>();
        allHistories = new ArrayList<>();
    }

    public void generate(List<String> domain, int nrOfDesiredTransactions) {
        //generate transactions using KRandomTransactionGenerator
        KRandomTransactionGenerator kRandomTransactionGenerator = new KRandomTransactionGenerator(transactionValidator);
//        transactions = kRandomTransactionGenerator.generate(domain, nrOfDesiredTransactions);
        transactions = generate3x3Transactions();
        //Generate permutation domain
        List<Operation> permutationDomain = generatePermutationDomain(transactions);
        int permutationDomainSize = permutationDomain.size();
        generateAllPermutations(permutationDomain.toArray(Operation[]::new), permutationDomainSize, permutationDomainSize);

        TransactionUtils.printWithIndex(allHistories);
    }

    private List<Operation> generatePermutationDomain(List<Transaction> transactions) {
        List<Operation> operations = new ArrayList<>();
        transactions.forEach(transaction -> operations.addAll(transaction.getOperations()));

        return operations;
    }

    @Override
    public void permutationGeneratedCallback(Operation[] operations) {
        Transaction transaction = (new Transaction(Arrays.asList(operations).subList(0, 9)));
        //if order kept, add to list
        if (transactionValidator.areOperationsInOriginalOrder(transactions, transaction)) {
            allHistories.add(transaction.toString());
        }
    }

    private List<Transaction> generate2x2Transactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        Operation r1 = new Operation();
        r1.setType(OperationType.READ);
        r1.setTransactionIdentifier(("0"));
        r1.setVariable("x");

        Operation w1 = new Operation();
        w1.setType(OperationType.WRITE);
        w1.setTransactionIdentifier("0");
        w1.setVariable("y");

        transaction1.getOperations().add(r1);
        transaction1.getOperations().add(w1);
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        Operation r2 = new Operation();
        r2.setType(OperationType.READ);
        r2.setTransactionIdentifier("1");
        r2.setVariable("y");

        Operation w2 = new Operation();
        w2.setType(OperationType.WRITE);
        w2.setTransactionIdentifier("1");
        w2.setVariable("w");

        transaction2.getOperations().add(r2);
        transaction2.getOperations().add(w2);
        transactions.add(transaction2);

        return transactions;
    }

    private List<Transaction> generate3x3Transactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        Operation r1 = new Operation();
        r1.setType(OperationType.READ);
        r1.setTransactionIdentifier(("0"));
        r1.setVariable("x");

        Operation w1 = new Operation();
        w1.setType(OperationType.WRITE);
        w1.setTransactionIdentifier("0");
        w1.setVariable("y");

        Operation w12 = new Operation();
        w12.setType(OperationType.WRITE);
        w12.setTransactionIdentifier("0");
        w12.setVariable("u");

        transaction1.getOperations().add(r1);
        transaction1.getOperations().add(w1);
        transaction1.getOperations().add(w12);
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        Operation r2 = new Operation();
        r2.setType(OperationType.READ);
        r2.setTransactionIdentifier("1");
        r2.setVariable("y");

        Operation w2 = new Operation();
        w2.setType(OperationType.WRITE);
        w2.setTransactionIdentifier("1");
        w2.setVariable("w");

        Operation r21 = new Operation();
        r21.setType(OperationType.READ);
        r21.setTransactionIdentifier("1");
        r21.setVariable("u");

        transaction2.getOperations().add(r2);
        transaction2.getOperations().add(r21);
        transaction2.getOperations().add(w2);
        transactions.add(transaction2);

        Transaction transaction3 = new Transaction();
        Operation r3 = new Operation();
        r3.setType(OperationType.READ);
        r3.setTransactionIdentifier("2");
        r3.setVariable("w");

        Operation r31 = new Operation();
        r31.setType(OperationType.READ);
        r31.setTransactionIdentifier("2");
        r31.setVariable("y");

        Operation w3 = new Operation();
        w3.setType(OperationType.WRITE);
        w3.setTransactionIdentifier("2");
        w3.setVariable("y");

        transaction3.getOperations().add(r3);
        transaction3.getOperations().add(r31);
        transaction3.getOperations().add(w3);
        transactions.add(transaction3);

        return transactions;
    }

}
