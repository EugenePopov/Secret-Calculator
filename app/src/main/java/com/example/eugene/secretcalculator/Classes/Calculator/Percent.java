package com.example.eugene.secretcalculator.Classes.Calculator;

/**
 * Created by Eugene on 25-May-16.
 */
public class Percent extends CalculatorBrain {
    public Percent(float firstOperand, float secondOperand, String operation){
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }

    @Override
    protected void compute(){
        result = firstOperand * (secondOperand/100);
    }
}
