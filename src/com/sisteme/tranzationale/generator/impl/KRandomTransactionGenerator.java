package com.sisteme.tranzationale.generator.impl;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.data.ValidationResponse;
import com.sisteme.tranzationale.util.TransactionUtils;
import com.sisteme.tranzationale.validator.TransactionValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KRandomTransactionGenerator {

    private static final int NR_OF_RETRIES = 100;

    private final TransactionValidator transactionValidator;

    public KRandomTransactionGenerator(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
    }

    public List<Transaction> generate(List<String> domain, int nrOfDesiredTransactions) {
        System.out.println("Started KRandomTransactionGenerator with NR OF DESIRED TRANSACTIONS: " + nrOfDesiredTransactions);
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        int nrOfRetries = NR_OF_RETRIES;

        while (!isGoalAchieved(nrOfDesiredTransactions, transactions.size())) {
            Operation operation = generateRandomOperation(domain);

            ValidationResponse validationResponse = getOperationValidator().isOperationValidForTransaction(operation, transaction);
            if (ValidationResponse.COMPLETE.equals(validationResponse)) {
                transaction.getOperations().forEach(operationIt -> operationIt.setTransactionIdentifier(String.valueOf(transactions.size())));
                transactions.add(transaction);
                transaction = new Transaction();
            } else if (ValidationResponse.ACCEPTED.equals(validationResponse)) {
                transaction.getOperations().add(operation);
            } else if (ValidationResponse.REJECTED.equals(validationResponse)){
                nrOfRetries--;
            }

            if (nrOfRetries <= 0) {
                transaction = new Transaction();
                nrOfRetries = NR_OF_RETRIES;
            }
        }

        TransactionUtils.print(transactions);
        return transactions;
    }

    private Operation generateRandomOperation(List<String> domain) {
        Operation operation = new Operation();
        operation.setType(extractRandomOperationType());
        operation.setVariable(extractRandomVariableFromDomain(domain));

        return operation;
    }

    private OperationType extractRandomOperationType() {
        List<OperationType> values = List.of(OperationType.values());
        Random random = new Random();

        return values.get(random.nextInt(values.size()));
    }

    private String extractRandomVariableFromDomain(List<String> domain) {
        Random random = new Random();

        return domain.get(random.nextInt(domain.size()));
    }

    private boolean isGoalAchieved(int nrOfDesiredTransactions, int nrOfGeneratedTransactions) {
        return nrOfDesiredTransactions == nrOfGeneratedTransactions;
    }

    public TransactionValidator getOperationValidator() {
        return transactionValidator;
    }
}
