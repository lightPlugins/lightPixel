package io.lightplugins.economy.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatter {

    public static BigDecimal formatBigDecimal(BigDecimal bd) {
        return bd.setScale(2, RoundingMode.HALF_UP);
    }

    public static double formatDouble(BigDecimal bd) {
        return formatBigDecimal(bd).doubleValue();
    }

    public static BigDecimal convertToBigDecimal(double d) {
        return formatBigDecimal(BigDecimal.valueOf(d));
    }

    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveNumber(double d) {
        return d > 0;
    }
}
