package com.example.eugene.secretcalculator.Classes.Calculator;

public class Sum extends CalculatorBrain {

    public Sum(){
        firstOperand = 0;
        secondOperand = 0;
        operation = "";
    }

    @Override
    public void compute(){
        result = firstOperand + secondOperand;
    }


}
