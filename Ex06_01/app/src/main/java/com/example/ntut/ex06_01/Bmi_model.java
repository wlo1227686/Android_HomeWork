package com.example.ntut.ex06_01;

import java.io.Serializable;
import java.text.NumberFormat;

public class Bmi_model implements Serializable {
    private double Height;
    private double Weight;


    public Bmi_model(double height, double weight) {
        Height = height;
        Weight = weight;

    }

    private String getBMI() {
        double BMI;
        String BMI_Zoom;
        String BMI_Result;

        BMI = Weight / Math.pow(Height / 100, 2);

        if (BMI >= 30) {
            BMI_Zoom = "Obese";
        } else if (BMI <= 25 && BMI < 30) {
            BMI_Zoom = "Overweight";
        } else if (BMI <= 18.5 && BMI < 25) {
            BMI_Zoom = "Normal weight";
        } else {
            BMI_Zoom = "Underweight";
        }

        /*設定 BMI 參數格式*/
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);//小數點後幾位

        BMI_Result = String.valueOf(nf.format(BMI)) + "\n" + BMI_Zoom;
        return BMI_Result;
    }

    @Override
    public String toString() {
        String result = "Height = " + Height +
                "\nWeight = " + Weight +
                "\nBMI = " + getBMI();
        return result;
    }
}
