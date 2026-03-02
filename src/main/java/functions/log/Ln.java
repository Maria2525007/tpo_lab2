package functions.log;

import functions.MathFunction;

public class Ln implements MathFunction {

    @Override
    public double calculate(double x, double eps) {

        if (x <= 0) {
            throw new IllegalArgumentException("ln undefined");
        }

        int k = 0;
        while (x > 2) {
            x /= Math.E;
            k++;
        }
        while (x < 0.5) {
            x *= Math.E;
            k--;
        }

        double y = (x - 1) / (x + 1);
        double result = 0;
        double term = y;
        int n = 1;

        while (Math.abs(term) > eps) {
            result += term / n;
            term *= y * y;
            n += 2;
        }

        return 2 * result + k;
    }
}