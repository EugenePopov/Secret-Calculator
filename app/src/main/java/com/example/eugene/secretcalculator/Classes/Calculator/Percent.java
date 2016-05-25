package com.example.eugene.secretcalculator.Classes.Calculator;

/**
 * Created by Eugene on 25-May-16.
 */
public class Percent extends CalculatorBrain {
    public Percent(){
        firstOperand = 0;
        secondOperand = 0;
        operation = "";
    }

    @Override
    public void compute(){
        result = firstOperand * (secondOperand/100);
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

    public float getResult(){
        return result;
    }
}
