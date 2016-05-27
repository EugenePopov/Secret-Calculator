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
