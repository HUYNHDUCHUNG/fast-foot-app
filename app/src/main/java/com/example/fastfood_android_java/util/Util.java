package com.example.fastfood_android_java.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Util {
    public static String formatCurrency(int s){
        NumberFormat formatter = new DecimalFormat("###,###,###,###");
        String formattedAmount = formatter.format(s);

        formattedAmount += "đ";

        return formattedAmount;
    }
}
