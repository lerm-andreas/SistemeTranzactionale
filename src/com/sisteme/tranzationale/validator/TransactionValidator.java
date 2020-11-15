package com.sisteme.tranzationale.validator;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.data.ValidationResponse;
import com.sisteme.tranzationale.helper.TransactionHelper;

import java.util.List;

public class TransactionValidator {

    private final int nrOfReads;
    private final int nrOfWrites;

    private final TransactionHelper transactionHelper;

    public TransactionValidator(int nrOfReads, int nrOfWrites, TransactionHelper transactionHelper) {
        this.nrOfReads = nrOfReads;
        this.nrOfWrites = nrOfWrites;
        this.transactionHelper = transactionHelper;
    }

    public boolean areOperationsInOriginalOrder(List<Transaction> originalTransactionList, Transaction transaction) {
        //IMPROVEMENT HERE => GET ALL POSSIBLE INDEXES
        for (int i=0; i<originalTransactionList.size(); i++) {
            List<Operation> operationsFromTransactionForIdentifier = transactionHelper.getAllOperationsFromTransactionForIdentifier(transaction, String.valueOf(i));
            //EXTRACT
            Transaction transaction1 = new Transaction();
            transaction1.setOperations(operationsFromTransactionForIdentifier);
            //REPLACE INDEX WITH TRANS IDENTIFIER
            for (Operation operation : transaction1.getOperations()) {
                operation.setTransactionIdentifier(String.valueOf(i));
            }

            if (!originalTransactionList.contains(transaction1)) {
                return false;
            }
        }

        return true;
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

    public int getNrOfWrites() {
        return nrOfWrites;
    }

    public TransactionHelper getTransactionHelper() {
        return transactionHelper;
    }

}
