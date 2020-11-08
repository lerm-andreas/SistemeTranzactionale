package com.sisteme.tranzationale.generator;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.data.ValidationResponse;
import com.sisteme.tranzationale.validator.OperationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KRandomTransactionGenerator {

    private static final int NR_OF_RETRIES = 100;

    private int nrOfDesiredTransactions;
    private List<String> domain;

    private OperationValidator operationValidator;

    public KRandomTransactionGenerator(int nrOfDesiredTransactions, List<String> domain, OperationValidator operationValidator) {
        this.nrOfDesiredTransactions = nrOfDesiredTransactions;
        this.domain = domain;
        this.operationValidator = operationValidator;
    }

    public List<Transaction> generate() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        int nrOfRetries = NR_OF_RETRIES;

        while (!isGoalAchieved(transactions.size())) {
            Operation operation = generateRandomOperation();

            ValidationResponse validationResponse = getOperationValidator().isOperationValidForTransaction(operation, transaction);
            if (ValidationResponse.COMPLETE.equals(validationResponse)) {
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

        displayTransactions(transactions);
        return transactions;
    }

    private void displayTransactions(List<Transaction> transactions) {
        transactions.forEach(transaction -> System.out.println(transaction.toString()));
    }

    private Operation generateRandomOperation() {
        Operation operation = new Operation();
        operation.setType(extractRandomOperationType());
        operation.setVariable(extractRandomVariableFromDomain());

        return operation;
    }

    private OperationType extractRandomOperationType() {
        List<OperationType> values = List.of(OperationType.values());
        Random random = new Random();

        return values.get(random.nextInt(values.size()));
    }

    private String extractRandomVariableFromDomain() {
        Random random = new Random();

        return getDomain().get(random.nextInt(getDomain().size()));
    }

    private boolean isGoalAchieved(int nrOfGeneratedTransactions) {
        return getNrOfDesiredTransactions() == nrOfGeneratedTransactions;
    }

    public int getNrOfDesiredTransactions() {
        return nrOfDesiredTransactions;
    }

    public void setNrOfDesiredTransactions(int nrOfDesiredTransactions) {
        this.nrOfDesiredTransactions = nrOfDesiredTransactions;
    }

    public List<String> getDomain() {
        return domain;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }

    public OperationValidator getOperationValidator() {
        return operationValidator;
    }

    public void setOperationValidator(OperationValidator operationValidator) {
        this.operationValidator = operationValidator;
    }
}
