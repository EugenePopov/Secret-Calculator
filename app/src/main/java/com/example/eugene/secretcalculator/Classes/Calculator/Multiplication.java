package com.example.eugene.secretcalculator.Classes.Calculator;

/**
 * Created by Eugene on 25-May-16.
 */
public class Multiplication extends CalculatorBrain {

    public Multiplication(){
        firstOperand = 0;
        secondOperand = 0;
        operation = "";
    }

    @Override
    public void compute(){
        result = firstOperand * secondOperand;
    }

}
