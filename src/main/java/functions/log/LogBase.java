package functions.log;

import functions.MathFunction;

public abstract class LogBase implements MathFunction {

    protected final Ln ln;
    protected final double base;

    protected LogBase(Ln ln, double base) {
        this.ln = ln;
        this.base = base;
    }

    @Override
    public double calculate(double x, double eps) {
        return ln.calculate(x, eps) / ln.calculate(base, eps);
    }
}