package com.example.eugene.secretcalculator.Classes.Calculator;


 public abstract class CalculatorBrain {

    protected float firstOperand;
    protected float secondOperand;
    protected String operation;
    protected float result;

    public CalculatorBrain(){
        firstOperand = 0;
        secondOperand = 0;
        operation = "";
    }


     public abstract void compute();
     public abstract void setFirstOperand(float firstOperand);
     public abstract void setSecondOperand(float secondOperand);
     public abstract void setOperation(String operation);
     public abstract float getResult();


}
