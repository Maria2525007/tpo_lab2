package functions.trig;

import functions.MathFunction;

public class Cos implements MathFunction {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {
        return sin.calculate(Math.PI / 2 - x, eps);
    }
}