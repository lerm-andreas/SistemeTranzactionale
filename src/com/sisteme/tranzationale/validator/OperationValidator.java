package com.sisteme.tranzationale.validator;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.data.ValidationResponse;
import com.sisteme.tranzationale.helper.TransactionHelper;

import java.util.List;

public class OperationValidator {

    private int nrOfReads;
    private int nrOfWrites;

    private TransactionHelper transactionHelper;

    public OperationValidator(int nrOfReads, int nrOfWrites, TransactionHelper transactionHelper) {
        this.nrOfReads = nrOfReads;
        this.nrOfWrites = nrOfWrites;
        this.transactionHelper = transactionHelper;
    }

    public boolean isTransactionValid(Transaction transaction) {
        Transaction newTransaction = new Transaction();

        for (Operation operation : transaction.getOperations()) {
            ValidationResponse validationResponse = isOperationValidForTransaction(operation, newTransaction);

            if (ValidationResponse.REJECTED.equals(validationResponse)) {
                return false;
            }

            newTransaction.getOperations().add(operation);
        }

        return getTransactionHelper().isTransactionComplete(newTransaction, getNrOfReads(), getNrOfWrites());
    }

    public ValidationResponse isOperationValidForTransaction(Operation operation, Transaction currentTransaction) {
        if (getTransactionHelper().isTransactionComplete(currentTransaction, getNrOfReads(), getNrOfWrites())) {
            return ValidationResponse.COMPLETE;
        }

        List<Operation> operationsFromTransactionForVariable = getTransactionHelper().getAllOperationsFromTransactionForVariable(currentTransaction, operation.getVariable());

        boolean atLeastOneWrite = operationsFromTransactionForVariable.stream()
                .anyMatch(op -> OperationType.WRITE.equals(op.getType()));

        if (atLeastOneWrite) {
            return ValidationResponse.REJECTED;
        }

        boolean atLeastOneRead = operationsFromTransactionForVariable.stream()
                .anyMatch(op -> OperationType.READ.equals(op.getType()));

        if (atLeastOneRead && operation.getType().equals(OperationType.READ)) {
            return ValidationResponse.REJECTED;
        }

        return ValidationResponse.ACCEPTED;
    }

    public int getNrOfReads() {
        return nrOfReads;
    }

    public void setNrOfReads(int nrOfReads) {
        this.nrOfReads = nrOfReads;
    }

    public int getNrOfWrites() {
        return nrOfWrites;
    }

    public void setNrOfWrites(int nrOfWrites) {
        this.nrOfWrites = nrOfWrites;
    }

    public TransactionHelper getTransactionHelper() {
        return transactionHelper;
    }

    public void setTransactionHelper(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }
}
