package com.example.eugene.secretcalculator.Classes.Calculator;

/**
 * Created by Eugene on 25-May-16.
 */
public class Percent implements Computable {


    public float compute(float firstOperand, float secondOperand){
        return firstOperand * (secondOperand/100);
    }

}
