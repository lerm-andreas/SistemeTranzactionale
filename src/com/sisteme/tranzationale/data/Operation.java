package com.sisteme.tranzationale.data;

public class Operation {

    private String variable;
    private String transactionIdentifier;
    private OperationType type;

    public Operation() {
    }

    public Operation(String variable, OperationType type) {
        this.variable = variable;
        this.type = type;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    @Override
    public String toString() {
        if ((transactionIdentifier != null) && (!transactionIdentifier.equals(""))) {
            return type + "(" + transactionIdentifier + ", " + variable.toUpperCase() + ")";
        } else {
            return type + "(" + variable.toUpperCase() + ")";
        }
    }
}
