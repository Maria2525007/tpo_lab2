package functions.trig;

import functions.MathFunction;

public class Sin implements MathFunction {

    @Override
    public double calculate(double x, double eps) {

        x = x % (2 * Math.PI);

        double term = x;
        double result = 0;
        int n = 1;

        while (Math.abs(term) > eps) {
            result += term;
            term *= -1 * x * x / ((2 * n) * (2 * n + 1));
            n++;
        }

        return result;
    }
}