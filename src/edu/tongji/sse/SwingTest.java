package edu.tongji.sse;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huage on 2017/4/5.
 */
public class SwingTest {

    public static void main(String[] args) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

        hashMap.put(1, 3);
        hashMap.get(1);

        System.out.println(hashMap.get(1));
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
