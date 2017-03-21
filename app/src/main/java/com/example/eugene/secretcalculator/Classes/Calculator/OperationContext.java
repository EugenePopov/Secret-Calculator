package com.example.eugene.secretcalculator.Classes.Calculator;


public class OperationContext {
    private Computable computable;

    protected float firstOperand;
    protected float secondOperand;
    protected String operation;


    public OperationContext(){
        firstOperand = 0;
        secondOperand = 0;
        operation = "";

    }

    public void setComputable(Computable computable){
        this.computable = computable;
    }

    public float executeComputable(){
        return computable.compute(firstOperand, secondOperand);
    }


    public void setFirstOperand(float firstOperand){
        this.firstOperand = firstOperand;
    }
    public void setSecondOperand(float secondOperand){
        this.secondOperand = secondOperand;
    }
    public void setOperation(String operation){
        this.operation = operation;
    }


}
