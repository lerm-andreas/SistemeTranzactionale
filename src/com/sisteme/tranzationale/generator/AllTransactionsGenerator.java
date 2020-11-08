package com.sisteme.tranzationale.generator;

import com.sisteme.tranzationale.data.Operation;
import com.sisteme.tranzationale.data.OperationType;
import com.sisteme.tranzationale.data.Transaction;
import com.sisteme.tranzationale.validator.OperationValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllTransactionsGenerator {
    private List<String> domain;
    private OperationValidator operationValidator;
    List<String> transactions;

    public AllTransactionsGenerator(List<String> domain, OperationValidator operationValidator) {
        this.domain = domain;
        this.operationValidator = operationValidator;
        transactions = new ArrayList<>();
    }

    public void generate() {
        List<Operation> permutationDomain = new ArrayList<>();
        domain.forEach(variable ->
                permutationDomain.addAll(List.of(new Operation(variable, OperationType.READ), new Operation(variable, OperationType.WRITE)))
        );

        int permutationDomainSize = permutationDomain.size();
        heapPermutation(permutationDomain.toArray(Operation[]::new), permutationDomainSize, permutationDomainSize);
        int counter = 1;

        for (String transaction : transactions) {
            System.out.println(counter + ": " + transaction);
            counter++;
        }
    }


    private void heapPermutation(Operation[] a, int size, int n) {
        if (size == 1) {
            Transaction transaction = (new Transaction(Arrays.asList(a).subList(0, 4)));
            if (operationValidator.isTransactionValid(transaction)) {
                String transactionToString = transaction.toString();
                if (transactions.contains(transactionToString)) {
                    transactions.add(transaction.toString());
                }
            }
        }

        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1, n);

            Operation temp;
            if (size % 2 == 1) {
                temp = a[0];
                a[0] = a[size - 1];
            } else {
                temp = a[i];
                a[i] = a[size - 1];
            }
            a[size - 1] = temp;
        }
    }
}
