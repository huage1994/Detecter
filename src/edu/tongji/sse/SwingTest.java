package edu.tongji.sse;

import javax.swing.*;

/**
 * Created by huage on 2017/4/5.
 */
public class SwingTest {

    public static void main(String[] args) {
        SwingTest swingTest = new SwingTest();
        swingTest.test();
    }
    public void test(){
        int height;
        int weight;
        String getweight;
        getweight = JOptionPane.showInputDialog(null, "Please enter your weight in Kilograms");
        String getheight;
        getheight = JOptionPane.showInputDialog(null, "Please enter your height in Centimeters");
        weight = Integer.parseInt(getweight);
        height = Integer.parseInt(getheight);
        double bmi;
        bmi = (weight/((height/100)^2));
        JOptionPane.showMessageDialog(null, "Your BMI is: " + bmi);
    }
}
