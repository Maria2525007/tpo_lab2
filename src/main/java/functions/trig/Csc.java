package functions.trig;

import functions.MathFunction;

public class Csc implements MathFunction {

    private final Sin sin;

    public Csc(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {

        double value = sin.calculate(x, eps);

        if (Math.abs(value) < eps) {
            throw new ArithmeticException("csc undefined");
        }

        return 1 / value;
    }
}