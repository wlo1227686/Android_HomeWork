package com.example.chenfu.ex08_02;

import java.io.Serializable;
import java.text.NumberFormat;

public class BMIbean implements Serializable {
    int id;
    double Height;
    double Weight;
    double BMI;
    String bmi;
    String BMI_Zoom;
    String CreateTime;

    public BMIbean(int id, double height, double weight, String bmi, String BMI_Zoom, String createTime) {
        this.id = id;
        Height = height;
        Weight = weight;
        this.bmi = bmi;
        this.BMI_Zoom = BMI_Zoom;
        CreateTime = createTime;
    }
    public BMIbean(double height, double weight) {
        Height = height;
        Weight = weight;
    }
    public String getBMI(){
        return bmi;
    }
    public String setBMI(){
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
        nf.setMaximumFractionDigits(1);//小數點後幾位

        return nf.format(BMI);
    }
    public String getBMI_Zoom(){
        return BMI_Zoom;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }
    public int getId(){
        return id;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
