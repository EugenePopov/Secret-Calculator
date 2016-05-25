package com.example.eugene.secretcalculator.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.example.eugene.secretcalculator.Classes.Calculator.*;
import com.example.eugene.secretcalculator.R;

public class CalculatorActivity extends AppCompatActivity {

    private CalculatorBrain calculatorEntity;
    private static String firstOperand = "0";
    private static String secondOperand = "";
    private static String inputOperation = " ";
    private static boolean isDotUsed = false;
    private static boolean isFirstOperandSet = false;
    private static boolean isFirstOperandNegative = false;
    private static boolean isSecondOperandNegative = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        hideNavigationBar();
        showOnDisplay(firstOperand, inputOperation);
    }

    private void hideNavigationBar() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

    /** Process digit buttons **/
    public void onButton0Click(View view){
        if(firstOperand.equals(0))firstOperand = "";
        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "0";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "0";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton1Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "1";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "1";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton2Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "2";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "2";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton3Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "3";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "3";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton4Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "4";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "4";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton5Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "5";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "5";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton6Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "6";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "6";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton7Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "7";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "7";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton8Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "8";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "8";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButton9Click(View view){
        if(firstOperand.equals("0"))firstOperand = "";

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11) firstOperand += "9";
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11) secondOperand += "9";
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    public void onButtonPlusMinusClick(View view){

        StringBuilder firstOperandBuilder = new StringBuilder(firstOperand);
        StringBuilder secondOperandBuilder = new StringBuilder(secondOperand);

        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11 && firstOperand.length() >= 1 && Float.parseFloat(firstOperand)!=0){
                if(!isFirstOperandNegative){
                    firstOperand = firstOperandBuilder.insert(0, "-").toString();
                    isFirstOperandNegative = true;
                    showOnDisplay(firstOperand, inputOperation);
                }
                else{
                    firstOperand = firstOperandBuilder.deleteCharAt(0).toString();
                    isFirstOperandNegative = false;
                    showOnDisplay(firstOperand, inputOperation);
                }
            }
        }
        else{
            if (secondOperand.length() < 11 && secondOperand.length() >= 1 && Float.parseFloat(secondOperand)!=0){
                if(!isSecondOperandNegative){
                    secondOperand = secondOperandBuilder.insert(0, "-").toString();
                    isSecondOperandNegative = true;
                    showOnDisplay(secondOperand, inputOperation);
                }
                else{
                    secondOperand = secondOperandBuilder.deleteCharAt(0).toString();
                    isSecondOperandNegative = false;
                    showOnDisplay(secondOperand, inputOperation);
                }
            }
        }
    }

    public void onButtonDotClick(View view){
        if(!isFirstOperandSet) {
            if (firstOperand.length() < 11 && !isDotUsed && firstOperand.length()>=1){
                firstOperand += ".";
                isDotUsed = true;
            }
            showOnDisplay(firstOperand, inputOperation);
        }
        else{
            if (secondOperand.length() < 11 && !isDotUsed && secondOperand.length()>=1){
                secondOperand += ".";
                isDotUsed = true;
            }
            showOnDisplay(secondOperand, inputOperation);
        }
    }

    /** Process operation buttons **/
    public void onButtonPlusClick(View view){
        isFirstOperandSet = true;
        isDotUsed = false;
        if(inputOperation.length()==1 && firstOperand.length()>0){
            calculatorEntity = new Sum();
            inputOperation = inputOperation.replace(inputOperation.charAt(0), '+');
            showOnDisplay(firstOperand, inputOperation);
        }
    }

    public void onButtonMinusClick(View view){
        isFirstOperandSet = true;
        isDotUsed = false;
        if(inputOperation.length()==1 && firstOperand.length()>0){
            calculatorEntity = new Subtraction();
            inputOperation = inputOperation.replace(inputOperation.charAt(0), '-');
            showOnDisplay(firstOperand, inputOperation);
        }
    }

    public void onButtonMultiplyClick(View view){
        isFirstOperandSet = true;
        isDotUsed = false;
        if(inputOperation.length()==1 && firstOperand.length()>0){
            calculatorEntity = new Multiplication();
            inputOperation = inputOperation.replace(inputOperation.charAt(0), 'x');
            showOnDisplay(firstOperand, inputOperation);
        }
    }

    public void onButtonDivideClick(View view){
        isFirstOperandSet = true;
        isDotUsed = false;
        if(inputOperation.length()==1 && firstOperand.length()>0){
            calculatorEntity = new Division();
            inputOperation = inputOperation.replace(inputOperation.charAt(0), '/');
            showOnDisplay(firstOperand, inputOperation);
        }
    }

    public void onButtonPercentClick(View view){
        isFirstOperandSet = true;
        isDotUsed = false;
        if(inputOperation.length()==1 && firstOperand.length()>0){
            calculatorEntity = new Percent();
            inputOperation = inputOperation.replace(inputOperation.charAt(0), '%');
            showOnDisplay(firstOperand, inputOperation);
        }
    }

    public void onButtonClearClick(View view){

        if (firstOperand.length() > 0 ) {
            firstOperand = "0";
        }
        if (inputOperation.length() > 0 ) {
            inputOperation = " ";
        }
        if (secondOperand.length() > 0 ) {
            secondOperand = "";
        }

        isDotUsed = false;
        isSecondOperandNegative = false;
        isFirstOperandNegative = false;
        isFirstOperandSet = false;
        showOnDisplay(firstOperand, inputOperation);
    }

    public void onButtonEqualClick(View view){
        if(secondOperand.length()>0) {
            calculatorEntity.setFirstOperand(Float.parseFloat(firstOperand));
            calculatorEntity.setSecondOperand(Float.parseFloat(secondOperand));
            calculatorEntity.setOperation(inputOperation);
            calculatorEntity.compute();
            Float result = calculatorEntity.getResult();
            processResult(result);
            showOnDisplay(firstOperand, inputOperation);
        }
//        showOnDisplay(result.toString(), " ");
/*        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);*/
    }

    /** Additional methods **/
    public void showOnDisplay(String number, String operation){
        setContentView(R.layout.activity_fullscreen);

        TextView digitalScreen = (TextView) findViewById(R.id.textView);
        TextView operationScreen = (TextView) findViewById(R.id.textView1);

//        setTextHeight(operationScreen);
//        setTextHeight(digitalScreen);

        digitalScreen.setText(number);
        operationScreen.setText(operation);
    }

    public void setTextHeight( final TextView textView) {

        ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
        if(viewTreeObserver.isAlive())

        {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    textView.setTextSize(textView.getHeight()-6);
                }
            });
        }

    }

    public boolean isResultInteger(Float result){
        String floatingValue;
        floatingValue = result.toString().substring(result.toString().indexOf(".")+1, result.toString().length());
        return floatingValue.equals("0");
    }

    public boolean isResultNegative(Float result){
        return result<0;
    }

    public void processResult(Float result){
        if(isResultInteger(result)){
            firstOperand = result.toString().substring(0, result.toString().indexOf("."));
            isDotUsed = false;
        }
        else{
            firstOperand = result.toString();
            isDotUsed = true;
        }
        if(isResultNegative(result)){
            isFirstOperandNegative = true;
        }
        else{
            isFirstOperandNegative = false;
        }
        isFirstOperandSet = true;
        isSecondOperandNegative = false;
        secondOperand = "";
        inputOperation = " ";
    }

}
