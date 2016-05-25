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

/*    private void computeSum(){
        result = firstOperand + secondOperand;
    }

    private void computeSubstraction(){
        result = firstOperand - secondOperand;
    }

    private void computeDivision(){
        result = firstOperand / secondOperand;
    }

    private void computeMultiplication(){
        result = firstOperand * secondOperand;
    }

    private void computePercentage(){
        result = firstOperand * (secondOperand/100);
    }*/

    abstract void compute();

}
