package io.vicp.frlib.algorithm.common;

/**
 * Created by zhoudr on 2017/3/28.
 */
public class SqrtNumber {
    public static double sqrt(int number, double gap) {
        double low = 0;
        double high = number;
        double middle;
        while (true) {
            middle = (low + high) / 2;
            double value = middle * middle;
            if (Math.abs(value-number) <= gap) {
                return middle;
            } else if (value - number > 0) {
                high = middle;
            } else if (value - number < 0) {
                low = middle;
            }
        }
    }

    public static void main(String[] args) {
        double value = SqrtNumber.sqrt(2, 0.001);
        System.out.println(value);
        System.out.println(Math.sqrt(2));
    }
}
