package com.sisteme.tranzationale.helper;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionHelper {

    public boolean isTransactionComplete(Transaction transaction, int nrOfReads, int nrOfWrites) {
        long nrOfReadsInCurrentTransaction = transaction.getOperations().stream()
                .filter(operation -> OperationType.READ.equals(operation.getType()))
                .count();

        return  nrOfReadsInCurrentTransaction == nrOfReads &&
                transaction.getOperations().size() - nrOfReadsInCurrentTransaction == nrOfWrites;
    }

    public List<Operation> getAllOperationsFromTransactionForVariable(Transaction transaction, String variable) {
        return transaction.getOperations().stream()
                .filter(operation -> operation.getVariable().equals(variable))
                .collect(Collectors.toList());
    }
}
