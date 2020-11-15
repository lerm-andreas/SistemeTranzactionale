package com.sisteme.tranzationale.generator;

import com.sisteme.tranzationale.data.Operation;

public interface PermutationService {

    default void generateAllPermutations(Operation[] operations, int size, int n) {
        if (size == 1) {
            this.permutationGeneratedCallback(operations);
        }

        for (int i = 0; i < size; i++) {
            generateAllPermutations(operations, size - 1, n);

            Operation temporaryOperation;
            if (size % 2 == 1) {
                temporaryOperation = operations[0];
                operations[0] = operations[size - 1];
            } else {
                temporaryOperation = operations[i];
                operations[i] = operations[size - 1];
            }

            operations[size - 1] = temporaryOperation;
        }
    }

    void permutationGeneratedCallback(Operation[] operations);
}
