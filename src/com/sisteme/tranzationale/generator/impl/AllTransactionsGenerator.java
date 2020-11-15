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

public class AllTransactionsGenerator implements PermutationService {

    private int nrOfReads;
    private int nrOfWrites;

    private final TransactionValidator transactionValidator;
    private final List<String> transactions;

    public AllTransactionsGenerator(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
        this.nrOfReads = 0;
        this.nrOfWrites = 0;
        transactions = new ArrayList<>();
    }

    public void generate(List<String> domain, int nrOfReads, int nrOfWrites) {
        System.out.println("Started AllTransactionsGenerator with NR OF READS: " + nrOfReads + " and NR OF WRITES: " + nrOfWrites);
        setNrOfReads(nrOfReads);
        setNrOfWrites(nrOfWrites);

        List<Operation> permutationDomain = generatePermutationDomain(domain);

        int permutationDomainSize = permutationDomain.size();
        generateAllPermutations(permutationDomain.toArray(Operation[]::new), permutationDomainSize, permutationDomainSize);

        TransactionUtils.printWithIndex(transactions);
    }

    private List<Operation> generatePermutationDomain(List<String> domain) {
        List<Operation> permutationDomain = new ArrayList<>();
        domain.forEach(variable ->
                permutationDomain.addAll(List.of(new Operation(variable, OperationType.READ), new Operation(variable, OperationType.WRITE)))
        );

        return permutationDomain;
    }

    @Override
    public void permutationGeneratedCallback(Operation[] operations) {
        Transaction transaction = (new Transaction(Arrays.asList(operations).subList(0, getNrOfReads() + getNrOfWrites())));
        if (!transactionValidator.isTransactionValid(transaction)) {
            return;
        }

        String transactionToString = transaction.toString();
        if (!transactions.contains(transactionToString)) {
            transactions.add(transaction.toString());
        }
    }

    public int getNrOfReads() {
        return nrOfReads;
    }

    public int getNrOfWrites() {
        return nrOfWrites;
    }

    public void setNrOfReads(int nrOfReads) {
        this.nrOfReads = nrOfReads;
    }

    public void setNrOfWrites(int nrOfWrites) {
        this.nrOfWrites = nrOfWrites;
    }
}
