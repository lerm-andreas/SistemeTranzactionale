package com.sisteme.tranzationale.data;

public class Operation {

    private String variable;
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

    @Override
    public String toString() {
        return  type + "(" + variable.toUpperCase() + ")";
    }
}
